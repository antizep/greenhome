package ru.antizep.greenhouse.serial;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SensorReading {

	private double airTemp;
	private double soilTemp;
	private LocalDateTime timestamp;
	private List<Humidity> soilHumidity = new ArrayList<>();

	public double getAirTemp() {
		return airTemp;
	}

	public void setAirTemp(double airTemp) {
		this.airTemp = airTemp;
	}

	public double getSoilTemp() {
		return soilTemp;
	}

	public void setSoilTemp(double soilTemp) {
		this.soilTemp = soilTemp;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public List<Humidity> getSoilHumidity() {
		return soilHumidity;
	}

	public void setSoilHumidity(List<Humidity> soilHumidity) {
		this.soilHumidity = soilHumidity;
	}

	public Optional<Humidity> getHumidityByZone(int id) {
		return soilHumidity.stream().filter(h -> h.getZoneId() == id).findFirst();
	}

}
