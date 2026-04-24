package ru.antizep.greenhouse.dto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.antizep.greenhouse.dto.entity.WateringLogEntity;

public interface WateringLogRepository extends JpaRepository<WateringLogEntity, Long> {

}
