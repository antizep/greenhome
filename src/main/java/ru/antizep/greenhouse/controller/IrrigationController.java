package ru.antizep.greenhouse.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.antizep.greenhouse.exception.HardwareSerialException;
import ru.antizep.greenhouse.service.IrrigationService;

@RestController
@RequestMapping("/api/irrigation")
@Tag(name = "Полив", description = "Управление насосами")
public class IrrigationController {
	private final IrrigationService irrigationService;

	public IrrigationController(IrrigationService irrigationService) {
		super();
		this.irrigationService = irrigationService;
	}
	
	@Operation(summary = "Запустить полив в указанной зоне", 
             description = "Отправляет команду на Arduino и записывает событие в БД")
	@PostMapping("/start")
	public void startWaterring(@RequestParam int zoneId) throws HardwareSerialException {
		irrigationService.startWatering(zoneId);
	}
}
