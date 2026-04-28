package ru.antizep.greenhouse.dto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "senssor_reading_humidity_by_zone")
public class HumidityByZone {
	@Id
	private long id;
	private GreenhouseZoneEntity zone;
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
	public SensorReadingEntity getSensor() {
		return sensor;
	}
	public void setSensor(SensorReadingEntity sensor) {
		this.sensor = sensor;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	
	
}
