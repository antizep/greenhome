package ru.antizep.greenhouse.serial.command;

public class ReadAllSensorCommand implements ArduinoCommand {

	@Override
	public String getCommandString() {
		return "GET:ALL#";
	}

}