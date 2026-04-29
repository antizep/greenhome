package ru.antizep.greenhouse.serial;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ru.antizep.greenhouse.exception.InvalidProtocolException;

class DataParserTest {

	private final SerialDataParser parser = new SerialDataParser();

	@Test
	void testCorrectData() throws InvalidProtocolException {
		String rawData = "TA=25.5;TS=18.2;H1=45.0;H2=38.0;H3=50.0;H4=42.0#";

		SensorReading result = parser.parse(rawData);

		assertEquals(25.5, result.getAirTemp());
		assertEquals(18.2, result.getSoilTemp());
		assertEquals(4, result.getSoilHumidity().size());
		assertEquals(42.0, result.getHumidityByZone(4).get().getValue());
	}

	@Test
	@DisplayName("Должен успешно парсить, если есть только один параметр H")
	void shouldParseWithSingleH() throws InvalidProtocolException {
		String input = "TA=10;TS=20;H3=55.5#";

		SensorReading result = parser.parse(input);

		assertEquals(55.5, result.getHumidityByZone(3).get().getValue());
		assertTrue(result.getHumidityByZone(4).isEmpty(), "H1 должен быть null или пуст");
	}

	@ParameterizedTest
	@ValueSource(strings = { "TA=22.5;TS=10.0;#", // Отсутствуют H
			"TS=10.0;H1=45.2;#", 				  // Нет обязательного TA
			"TA=abc;TS=10.0;H1=45.2#",            // Не число в TA
			"TA=22.5;TS=10.0;H1=45.2", 			  // Нет символа конца #
			"   ",								  // Пустая строка
	})
	@DisplayName("Должен выбрасывать исключение при неверном формате")
	void shouldThrowExceptionOnInvalidFormat(String invalidInput) {
		assertThrows(InvalidProtocolException.class, () -> {
			parser.parse(invalidInput);
		});
	}
}
