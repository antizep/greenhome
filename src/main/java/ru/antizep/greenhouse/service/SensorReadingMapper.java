package ru.antizep.greenhouse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;
import ru.antizep.greenhouse.dto.entity.HumidityByZone;
import ru.antizep.greenhouse.dto.entity.SensorReadingEntity;
import ru.antizep.greenhouse.exception.ZoneNotFounException;
import ru.antizep.greenhouse.serial.Humidity;
import ru.antizep.greenhouse.serial.SensorReading;

public class SensorReadingMapper {

	private GreenhouseZoneRepository zoneRepository;

	public SensorReadingMapper(GreenhouseZoneRepository zoneRepository) {
		super();
		this.zoneRepository = zoneRepository;
	}

	public SensorReadingEntity mapSensorEntity(SensorReading reading) {

		SensorReadingEntity resultEntity = new SensorReadingEntity();
		resultEntity.setAirTemp(reading.getAirTemp());
		resultEntity.setSoilTemp(reading.getSoilTemp());
		resultEntity.setHumidityByZone(mapHumidityByZones(reading));
		return resultEntity;
	}

	private List<HumidityByZone> mapHumidityByZones(SensorReading reading) throws ZoneNotFounException {
		List<HumidityByZone> result = new ArrayList<HumidityByZone>();

		List<Humidity> humidities = reading.getSoilHumidity();

		for (Humidity h : humidities) {
			long id = (long) h.getZoneId();
			double value = h.getValue();
			
			HumidityByZone humidityByZone = new HumidityByZone();
			humidityByZone.setHumidity(value);

			Optional<GreenhouseZoneEntity> zoneEntityOptional = zoneRepository.findById(id);
			if (zoneEntityOptional.isEmpty()) {
				throw new ZoneNotFounException("Зона не зарегистрирована id:" + id);
			}
			humidityByZone.setZone(zoneEntityOptional.get());
			result.add(humidityByZone);
		}
		return result;

	}
}
