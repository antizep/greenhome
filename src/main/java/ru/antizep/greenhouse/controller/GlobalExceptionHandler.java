package ru.antizep.greenhouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.antizep.greenhouse.dto.ErrorApiDto;
import ru.antizep.greenhouse.dto.ErrorCodeHandbook;
import ru.antizep.greenhouse.exception.HardwareSerialException;
import ru.antizep.greenhouse.exception.InvalidProtocolException;
import ru.antizep.greenhouse.exception.ZoneNotFounException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(HardwareSerialException.class)
    public ResponseEntity<ErrorApiDto> handleArduinoException(HardwareSerialException ex) {
		ErrorApiDto error = new ErrorApiDto();
		error.setCode(ErrorCodeHandbook.HARDWARE_NOT_RESPOND.getCode());
		error.setMessage("Ошибка управления теплицей: " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(error);
    }
	
	@ExceptionHandler(InvalidProtocolException.class)
	public ResponseEntity<ErrorApiDto> handleInvalidProtocolException(InvalidProtocolException ex){
		ErrorApiDto error = new ErrorApiDto();
		error.setCode(ErrorCodeHandbook.UNEXPECTED_RESPONSE.getCode());
		error.setMessage("Ошибка обработки данных из serial: "+ ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(error);
	}
	
	@ExceptionHandler(ZoneNotFounException.class)
	public ResponseEntity<ErrorApiDto> handleZoneNotFoundException(ZoneNotFounException ex){
		ErrorApiDto error = new ErrorApiDto();
		error.setCode(ErrorCodeHandbook.ZONE_NOT_FOUND.getCode());
		error.setMessage("Контроллер вернул незарегистрированую зону: "+ ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(error);
	}
}
