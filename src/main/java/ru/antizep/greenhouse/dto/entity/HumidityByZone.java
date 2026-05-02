package ru.antizep.greenhouse.dto.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor_reading_humidity_by_zone")
public class HumidityByZone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
	private GreenhouseZoneEntity zone;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_reading_id", nullable = false)
    private SensorReadingEntity sensor;
    
	private Double humidity;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public GreenhouseZoneEntity getZone() {
		return zone;
	}
	public void setZone(GreenhouseZoneEntity zone) {
		this.zone = zone;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	public SensorReadingEntity getSensor() {
		return sensor;
	}
	public void setSensor(SensorReadingEntity sensor) {
		this.sensor = sensor;
	}
	
	
}
