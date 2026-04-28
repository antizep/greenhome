package ru.antizep.greenhouse.exception;

public class ArduinoException extends RuntimeException {
	public ArduinoException(String errorMessage) {
		super(errorMessage);
	}
}
