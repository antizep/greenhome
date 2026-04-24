package ru.antizep.greenhouse;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.antizep.greenhous.service.IrrigationService;
import ru.antizep.greenhouse.dto.repository.WateringLogRepository;
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
		String expectedCommand = "PUMP:ON:1#";
		when(gateway.sendAndReceive(expectedCommand)).thenReturn("OK#");


		service.startWatering(zoneId);

		verify(repository).save(argThat(
				log -> log.getZoneId() == zoneId && "START".equals(log.getEvent()) && log.getTimestamp() != null));
	}
}
