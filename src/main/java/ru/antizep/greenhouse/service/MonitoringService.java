package ru.antizep.greenhouse.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;
import ru.antizep.greenhouse.dto.entity.SensorEntity;
import ru.antizep.greenhouse.dto.repository.SensorReadingRepository;
import ru.antizep.greenhouse.serial.SensorReading;
import ru.antizep.greenhouse.serial.SerialDataParser;
import ru.antizep.greenhouse.serial.command.ReadAllSensorCommand;

public class MonitoringService {

	private final ArduinoGateway gateway;
	private final SerialDataParser parser;
	private final SensorReadingRepository repository;
	public MonitoringService(ArduinoGateway gateway, SerialDataParser parser, SensorReadingRepository repository) {
		this.gateway = gateway;
		this.parser = parser;
		this.repository = repository;
	}

	public void fetchCurrentData() {
		String response = gateway.sendAndReceive(new ReadAllSensorCommand());
		SensorReading reading = parser.parse(response);	
		repository.save(mapSensorEntity(reading));
	}

	private SensorEntity mapSensorEntity(SensorReading reading) {

		SensorEntity resultEntity = new SensorEntity();
		resultEntity.setAirTemp(reading.getAirTemp());
		resultEntity.setSoilTemp(reading.getSoilTemp());
		resultEntity.setZoneHumidity(mapZeone(reading));
		return resultEntity;
	}
	
	private Map<GreenhouseZoneEntity, Double> mapZeone(SensorReading reading) {
		Map<GreenhouseZoneEntity, Double> resultMap = new HashMap<GreenhouseZoneEntity, Double>();
		List<Double> humidity = reading.getSoilHumidity();
		
		for (int i=0; i<reading.getSoilHumidity().size();i++){
			GreenhouseZoneEntity zoneEntity = new GreenhouseZoneEntity();
			zoneEntity.setId((long) i);
			resultMap.put(new GreenhouseZoneEntity(), humidity.get(i));
		}
		return resultMap;
		
	}
}
