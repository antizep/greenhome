package ru.antizep.greenhouse.serial.command;

public class GetAllCommand implements ArduinoCommand {

	@Override
	public String getCommandString() {
		return "GET:ALL#";
	}

}