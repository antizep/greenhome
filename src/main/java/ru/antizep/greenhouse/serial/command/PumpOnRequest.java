package ru.antizep.greenhouse.serial.command;

import java.util.Objects;

public class PumpOnRequest implements ArduinoCommand {

	private final long zoneId;
	
	public PumpOnRequest(long zoneId) {
		super();
		this.zoneId = zoneId;
	}

	@Override
	public String getCommandString() {
		 return "PUMP:ON:" + zoneId + "#";
	}

	@Override
	public int hashCode() {
		return Objects.hash(zoneId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PumpOnRequest other = (PumpOnRequest) obj;
		return zoneId == other.zoneId;
	}
	
}
