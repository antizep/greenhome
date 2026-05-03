package ru.antizep.greenhouse.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;
import ru.antizep.greenhouse.dto.repository.WateringLogRepository;
import ru.antizep.greenhouse.serial.command.ArduinoCommand;
import ru.antizep.greenhouse.serial.command.PumpOnRequest;
import ru.antizep.greenhouse.exception.HardwareSerialException;
import ru.antizep.greenhouse.exception.ZoneNotFounException;

@ExtendWith(MockitoExtension.class)
public class IrrigationServiceTest {
	@Mock
	private ArduinoGateway gateway;

	@Mock
	private WateringLogRepository repository;
	@Mock
	private GreenhouseZoneRepository zoneRepository;

	private IrrigationService service;

	@BeforeEach
	void setUp() {
		service = new IrrigationService(gateway, repository, zoneRepository);
	}

	@Test
	void shouldLogWateringWhenArduinoRespondsZoneNotFound() throws HardwareSerialException {
		long zoneId = 1;
		assertThrows(ZoneNotFounException.class, () -> service.startWatering(zoneId));
	}

	@Test
	void shouldLogWateringWhenArduinoNotResponds() throws HardwareSerialException {
		when(gateway.sendAndReceive(any())).thenThrow(new HardwareSerialException("ControllerNotRespond"));	
		assertThrows(HardwareSerialException.class, () -> service.startWatering(1));
	}
	
	@Test
	void shouldLogWateringWhenArduinoRespondsOk() throws HardwareSerialException {

		long zoneId = 1;
		ArduinoCommand expectedCommand = new PumpOnRequest(zoneId);
		when(gateway.sendAndReceive(expectedCommand)).thenReturn("OK#");
		when(zoneRepository.findById(zoneId)).thenReturn(Optional.of(new GreenhouseZoneEntity(zoneId,null, null, 0)));
		
		service.startWatering(zoneId);

		verify(repository).save(argThat(log -> log.getZone().getId() == zoneId && "START".equals(log.getEvent())
				&& log.getTimestamp() != null));
	}

	@Test
	void shouldLogFailureWhenArduinoRespondsError() throws HardwareSerialException {

		long zoneId = 1;
		when(gateway.sendAndReceive(new PumpOnRequest(zoneId))).thenReturn("ERROR#");
		when(zoneRepository.findById(zoneId)).thenReturn(Optional.of(new GreenhouseZoneEntity(zoneId,null, null, 0)));
		
		assertThrows(HardwareSerialException.class, () -> service.startWatering(zoneId));

		verify(repository).save(argThat(log -> log.getZone().getId() == zoneId && "ERROR".equals(log.getEvent())));
	}
}
