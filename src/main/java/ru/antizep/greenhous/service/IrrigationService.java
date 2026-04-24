package ru.antizep.greenhous.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.entity.WateringLogEntity;
import ru.antizep.greenhouse.dto.repository.WateringLogRepository;
import ru.antizep.greenhouse.serial.command.PumpOnRequest;

@Service
public class IrrigationService {
	private final ArduinoGateway arduinoGateway;
	private final WateringLogRepository wateringLogRepository;

	public IrrigationService(ArduinoGateway arduinoGateway, WateringLogRepository wateringLogRepository) {
		this.arduinoGateway = arduinoGateway;
		this.wateringLogRepository = wateringLogRepository;
	}

	public void startWatering(int zoneId) {
		String response = arduinoGateway.sendAndReceive(new PumpOnRequest(zoneId));

		if ("OK#".equals(response)) {
			WateringLogEntity logEntry = new WateringLogEntity(zoneId, "START", LocalDateTime.now());
			wateringLogRepository.save(logEntry);
		}
	}
}
