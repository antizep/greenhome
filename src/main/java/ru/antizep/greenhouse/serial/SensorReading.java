package ru.antizep.greenhouse.serial;

import java.time.LocalDateTime;
import java.util.List;

public class SensorReading {
	
	private double airTemp;
	private double soilTemp;
	private LocalDateTime timestamp;
	private List<Double> soilHumidity;
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
	public List<Double> getSoilHumidity() {
		return soilHumidity;
	}
	public void setSoilHumidity(List<Double> soilHumidity) {
		this.soilHumidity = soilHumidity;
	}
	
}
