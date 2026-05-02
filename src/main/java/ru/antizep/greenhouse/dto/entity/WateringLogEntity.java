package ru.antizep.greenhouse.dto.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "watering_logs")
public class WateringLogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String event;
	@Column(name= "event_time")
	private LocalDateTime timestamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zone_id")
	private GreenhouseZoneEntity zone;
	
	public WateringLogEntity() {
	}

	public WateringLogEntity(GreenhouseZoneEntity zone, String event, LocalDateTime timestamp) {
		this.event = event;
		this.timestamp = timestamp;
		this.zone = zone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public GreenhouseZoneEntity getZone() {
		return zone;
	}

	public void setZone(GreenhouseZoneEntity zone) {
		this.zone = zone;
	}

}
