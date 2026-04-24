package ru.antizep.greenhouse.serial;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SerialDataParser {
	
	 public SensorReading parse(String rawData) {
		 
	    String cleanData = rawData.replace("#", "");
	    
	    Map<String, String> map = extractParam(cleanData);
	
	    SensorReading r = new SensorReading();
	    r.setAirTemp(Double.parseDouble(map.getOrDefault("TA", "0")));
	    r.setSoilTemp(Double.parseDouble(map.getOrDefault("TS", "0")));
	    
	    List<Double> zonesHumidity = parseHumidity(map);
	    
	    r.setSoilHumidity(zonesHumidity);
	    r.setTimestamp(LocalDateTime.now());
	    return r;
	}

	 private List<Double> parseHumidity(Map<String, String> map) {
		List<Double> zones = map.keySet().stream()
	            .filter(k -> k.startsWith("H"))
	            .sorted()
	            .map(k -> Double.parseDouble(map.get(k)))
	            .collect(Collectors.toList());
		return zones;
	 }

	 private Map<String, String> extractParam(String cleanData) {
		Map<String, String> map = Arrays.stream(cleanData.split(";"))
	            .map(s -> s.split("="))
	            .filter(a -> a.length == 2)
	            .collect(Collectors.toMap(a -> a[0], a -> a[1]));
		return map;
	 }
	 
}
