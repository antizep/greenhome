package ru.antizep.greenhouse.dto;

public enum ErrorCodeHandbook {

	UNEXPECTED_RESPONSE("SER-001"), 
	ZONE_NOT_FOUND("BUS-001"),
	HARDWARE_NOT_RESPOND("SER-002");

	private final String code;

	ErrorCodeHandbook(String code) { this.code = code; }

	public String getCode() {
		return code;
	}
}
