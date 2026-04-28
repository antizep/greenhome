package ru.antizep.greenhouse.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;

public interface GreenhouseZoneRepository extends JpaRepository<GreenhouseZoneEntity, Long>{
	
}
