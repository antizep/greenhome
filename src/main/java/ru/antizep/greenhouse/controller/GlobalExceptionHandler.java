package ru.antizep.greenhouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.antizep.greenhouse.exception.ArduinoException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ArduinoException.class)
    public ResponseEntity<String> handleArduinoException(ArduinoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body("Ошибка управления теплицей: " + ex.getMessage());
    }
}
