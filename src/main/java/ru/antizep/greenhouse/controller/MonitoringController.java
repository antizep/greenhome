package ru.antizep.greenhouse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.antizep.greenhouse.exception.InvalidProtocolException;
import ru.antizep.greenhouse.service.MonitoringService;

@RestController
@RequestMapping("/api/monitoring")
@Tag(name = "Мониторинг", description = "Сбор данных с датчиков теплицы")
public class MonitoringController {
	
	private final MonitoringService sensorService;
	
	
	public MonitoringController(MonitoringService sensorService) {
		super();
		this.sensorService = sensorService;
	}

	@Operation(summary = "Принудительный опрос датчиков")
	@RequestMapping("/fetch")
	public void collectData() throws InvalidProtocolException {
		sensorService.fetchCurrentData();
	}
}
