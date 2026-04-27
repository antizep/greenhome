package ru.antizep.greenhouse.dto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.antizep.greenhouse.dto.entity.SensorEntity;


public interface SensorReadingRepository extends JpaRepository<SensorEntity, Long>{

}
