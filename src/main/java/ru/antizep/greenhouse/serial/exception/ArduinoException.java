package ru.antizep.greenhouse.serial.exception;

public class ArduinoException extends RuntimeException {
	public ArduinoException(String errorMessage) {
		super(errorMessage);
	}
}
