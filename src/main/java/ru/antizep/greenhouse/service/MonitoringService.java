package ru.antizep.greenhouse.service;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.repository.SensorReadingRepository;
import ru.antizep.greenhouse.serial.SensorReading;
import ru.antizep.greenhouse.serial.SerialDataParser;
import ru.antizep.greenhouse.serial.command.ReadAllSensorCommand;

public class MonitoringService {

	private final ArduinoGateway gateway;
	private final SerialDataParser parser;
	private final SensorReadingMapper sensorReadingMapper;
	private final SensorReadingRepository repository;
	public MonitoringService(ArduinoGateway gateway, SerialDataParser parser, SensorReadingRepository repository,SensorReadingMapper sensorReadingMapper) {
		this.gateway = gateway;
		this.parser = parser;
		this.repository = repository;
		this.sensorReadingMapper = sensorReadingMapper;
	}

	public void fetchCurrentData() {
		String response = gateway.sendAndReceive(new ReadAllSensorCommand());
		SensorReading reading = parser.parse(response);	
		repository.save(sensorReadingMapper.mapSensorEntity(reading));
	}


}
