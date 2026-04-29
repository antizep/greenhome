package ru.antizep.greenhouse.serial;

public class Humidity {
	private final int zoneId;
	private final double value;

	public Humidity(int zoneId, double value) {
		this.zoneId = zoneId;
		this.value = value;
	}

	public int getZoneId() {
		return zoneId;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Zone " + zoneId + ": " + value + "%";
	}
}
