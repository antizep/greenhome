package ru.antizep.greenhouse.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;
import ru.antizep.greenhouse.dto.entity.WateringLogEntity;
import ru.antizep.greenhouse.dto.repository.WateringLogRepository;
import ru.antizep.greenhouse.serial.command.PumpOnRequest;
import ru.antizep.greenhouse.exception.HardwareSerialException;
import ru.antizep.greenhouse.exception.ZoneNotFounException;

@Service
public class IrrigationService {
	private final ArduinoGateway arduinoGateway;
	private final WateringLogRepository wateringLogRepository;
	private final GreenhouseZoneRepository greenhouseZoneRepository;
	
	public IrrigationService(ArduinoGateway arduinoGateway, WateringLogRepository wateringLogRepository,
			GreenhouseZoneRepository greenhouseZoneRepository) {
		super();
		this.arduinoGateway = arduinoGateway;
		this.wateringLogRepository = wateringLogRepository;
		this.greenhouseZoneRepository = greenhouseZoneRepository;
	}

	public void startWatering(long zoneId) throws HardwareSerialException {
		String response = arduinoGateway.sendAndReceive(new PumpOnRequest(zoneId));
		GreenhouseZoneEntity zone = findZone(zoneId);
		if ("OK#".equals(response)) {
			WateringLogEntity logEntry = new WateringLogEntity(zone, "START", LocalDateTime.now());
			wateringLogRepository.save(logEntry);
		} else {
			wateringLogRepository.save(new WateringLogEntity(zone, "ERROR", LocalDateTime.now()));
			throw new HardwareSerialException("Ошибка обработки команды полива в зоне " + zoneId);
		}
	}
	private GreenhouseZoneEntity findZone(long zoneId) {
		Optional<GreenhouseZoneEntity> zone = greenhouseZoneRepository.findById(zoneId);
		return zone.orElseThrow(()-> new ZoneNotFounException("Зона "+zoneId+" не зарегистрироана") );
	}
}
