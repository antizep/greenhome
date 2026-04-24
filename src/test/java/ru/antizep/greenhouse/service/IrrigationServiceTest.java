package ru.antizep.greenhouse.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.repository.WateringLogRepository;
import ru.antizep.greenhouse.serial.command.ArduinoCommand;
import ru.antizep.greenhouse.serial.command.PumpOnRequest;
import ru.antizep.greenhouse.serial.exception.ArduinoException;

@ExtendWith(MockitoExtension.class)
public class IrrigationServiceTest {
	@Mock
	private ArduinoGateway gateway;

	@Mock
	private WateringLogRepository repository;

	private IrrigationService service;

	@BeforeEach
	void setUp() {
		service = new IrrigationService(gateway, repository);
	}

	@Test
	void shouldLogWateringWhenArduinoRespondsOk() {

		int zoneId = 1;
		ArduinoCommand expectedCommand = new PumpOnRequest(zoneId);
		when(gateway.sendAndReceive(expectedCommand)).thenReturn("OK#");


		service.startWatering(zoneId);

		verify(repository).save(argThat(
				log -> log.getZoneId() == zoneId && "START".equals(log.getEvent()) && log.getTimestamp() != null));
	}
	
	@Test
	void shouldLogFailureWhenArduinoRespondsError() {
		
	    int zoneId = 1;
	    when(gateway.sendAndReceive(new PumpOnRequest(zoneId))).thenReturn("ERROR#");

	    assertThrows(ArduinoException.class, () -> service.startWatering(zoneId));
	    
	    verify(repository).save(argThat(log -> 
	        log.getZoneId() == zoneId && 
	        "ERROR".equals(log.getEvent()) // Проверяем статус ошибки в логе
	    ));
	}
}
