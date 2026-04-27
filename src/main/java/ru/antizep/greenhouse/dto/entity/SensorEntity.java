package ru.antizep.greenhouse.dto.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor_readings")
public class SensorEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "air_temp")
    private Double airTemp;

    @Column(name = "soil_temp")
    private Double soilTemp;
	
    @ElementCollection
    @CollectionTable(name = "zone_humidity_readings", joinColumns = @JoinColumn(name = "CUST_ID"))
    @MapKeyJoinColumn(name = "zone_id")
    @Column(name = "humidity_value")
    private Map<GreenhouseZoneEntity, Double> zoneHumidity = new HashMap<>();

    private LocalDateTime timestamp;

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

	public Map<GreenhouseZoneEntity, Double> getZoneHumidity() {
		return zoneHumidity;
	}

	public void setZoneHumidity(Map<GreenhouseZoneEntity, Double> zoneHumidity) {
		this.zoneHumidity = zoneHumidity;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

    
    
}
