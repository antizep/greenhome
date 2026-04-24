package ru.antizep.greenhous.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.entity.WateringLogEntity;
import ru.antizep.greenhouse.dto.repository.WateringLogRepository;

@Service
public class IrrigationService {
	private final ArduinoGateway arduinoGateway;
	private final WateringLogRepository wateringLogRepository;
	
	public IrrigationService(ArduinoGateway arduinoGateway, WateringLogRepository wateringLogRepository) {
		super();
		this.arduinoGateway = arduinoGateway;
		this.wateringLogRepository = wateringLogRepository;
	}

    public void startWatering(int zoneId) {
        String command = "PUMP:ON:" + zoneId + "#";
        
        String response = arduinoGateway.sendAndReceive(command);

        if ("OK#".equals(response)) {
            WateringLogEntity logEntry = new WateringLogEntity(
                zoneId, 
                "START", 
                LocalDateTime.now()
            );
            wateringLogRepository.save(logEntry);
        }
    }
}
