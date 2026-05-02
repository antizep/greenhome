package ru.antizep.greenhouse.dto.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor_readings")
public class SensorReadingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "air_temp")
    private Double airTemp;

    @Column(name = "soil_temp")
    private Double soilTemp;
	
    private LocalDateTime timestamp;
    
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HumidityByZone> humidityByZone;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAirTemp() {
		return airTemp;
	}

	public void setAirTemp(Double airTemp) {
		this.airTemp = airTemp;
	}

	public Double getSoilTemp() {
		return soilTemp;
	}

	public void setSoilTemp(Double soilTemp) {
		this.soilTemp = soilTemp;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public List<HumidityByZone> getHumidityByZone() {
		return humidityByZone;
	}

	public void setHumidityByZone(List<HumidityByZone> humidityByZone) {
		this.humidityByZone = humidityByZone;
	}

    
    
}
