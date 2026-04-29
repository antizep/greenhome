package ru.antizep.greenhouse.serial;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ru.antizep.greenhouse.exception.InvalidProtocolException;

public class SerialDataParser {
	private static final Pattern PROTOCOL_PATTERN = Pattern.compile("TA=([^;]+);TS=([^;]+);(.*)#");

	public SensorReading parse(String rawData) throws InvalidProtocolException {
		Matcher matcher = PROTOCOL_PATTERN.matcher(rawData);

		if (!matcher.matches()) {
			throw new InvalidProtocolException("Строка не соответствует формату протокола");
		}
		try {

			double ta = Double.parseDouble(matcher.group(1));
			double ts = Double.parseDouble(matcher.group(2));

			SensorReading r = new SensorReading();
			r.setAirTemp(ta);
			r.setSoilTemp(ts);

			String hPart = matcher.group(3);
			if (hPart.isEmpty() || !hPart.contains("H")) {
				throw new InvalidProtocolException("Отсутствуют обязательные параметры H");
			}
			r.setSoilHumidity(parseHumidityList(hPart));
			r.setTimestamp(LocalDateTime.now());
			return r;
		} catch (Exception e) {
			throw new InvalidProtocolException("Ошибка преобразования показаний датчиков");
		}
	}

	private List<Humidity> parseHumidityList(String hPart) {
	    List<Humidity> list = new ArrayList<>();
	    Pattern p = Pattern.compile("H(?<id>\\d+)=(?<val>[-+]?\\d*\\.?\\d+)");
	    Matcher m = p.matcher(hPart);

	    while (m.find()) {
	        int id = Integer.parseInt(m.group("id"));
	        double val = Double.parseDouble(m.group("val"));
	        list.add(new Humidity(id, val));
	    }
	    return list;
	}

}
