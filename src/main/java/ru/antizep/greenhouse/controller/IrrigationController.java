package ru.antizep.greenhouse.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.antizep.greenhouse.service.IrrigationService;

@RestController
@RequestMapping("/api/irrigation")
public class IrrigationController {
	private final IrrigationService irrigationService;

	public IrrigationController(IrrigationService irrigationService) {
		super();
		this.irrigationService = irrigationService;
	}
	@PostMapping("/start")
	public void startWaterring(@RequestParam int zoneId) {
		irrigationService.startWatering(zoneId);
	}
}
