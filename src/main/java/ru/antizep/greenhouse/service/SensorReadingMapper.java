package ru.antizep.greenhouse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;
import ru.antizep.greenhouse.dto.entity.HumidityByZone;
import ru.antizep.greenhouse.dto.entity.SensorReadingEntity;
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
	
	private List<HumidityByZone> mapHumidityByZones(SensorReading reading) {
		List<HumidityByZone> result = new ArrayList<HumidityByZone>();
		
		List<Double> humidities = reading.getSoilHumidity();
		
		for (int i = 0; i<humidities.size();i++){
			
			HumidityByZone humidityByZone = new HumidityByZone();
			humidityByZone.setHumidity(humidities.get(i));
			humidityByZone.setZone(zoneRepository.findById((long)i+1).get());
			result.add(humidityByZone);
		}
		return result;
		
	}
}
