package ru.antizep.greenhouse.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorApiDto {
	private UUID correlationId;
	private String code;
	private String message;
	public UUID getCorrelationId() {
		return correlationId;
	}
	
	public void setCorrelationId(UUID correlationId) {
		this.correlationId = correlationId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
