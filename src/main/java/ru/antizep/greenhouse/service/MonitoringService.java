package ru.antizep.greenhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.repository.SensorReadingRepository;
import ru.antizep.greenhouse.exception.InvalidProtocolException;
import ru.antizep.greenhouse.serial.SensorReading;
import ru.antizep.greenhouse.serial.SerialDataParser;
import ru.antizep.greenhouse.serial.command.ReadAllSensorCommand;
@Service
public class MonitoringService {


	private final ArduinoGateway gateway;
	private final SerialDataParser parser;
	private final SensorReadingMapper sensorReadingMapper;
	private final SensorReadingRepository repository;
    @Autowired	
	public MonitoringService(ArduinoGateway gateway, SerialDataParser parser, SensorReadingRepository repository,SensorReadingMapper sensorReadingMapper) {
		this.gateway = gateway;
		this.parser = parser;
		this.repository = repository;
		this.sensorReadingMapper = sensorReadingMapper;
	}

	public void fetchCurrentData() throws InvalidProtocolException {
		String response = gateway.sendAndReceive(new ReadAllSensorCommand());
		SensorReading reading = parser.parse(response);	
		repository.save(sensorReadingMapper.mapSensorEntity(reading));
	}


}
