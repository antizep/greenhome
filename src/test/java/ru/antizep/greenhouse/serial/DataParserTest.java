package ru.antizep.greenhouse.serial;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataParserTest {

	private final SerialDataParser parser = new SerialDataParser();

	@Test
	void testCorrectData() {
		 // Входящая строка от Arduino
	    String rawData = "TA=25.5;TS=18.2;H1=45.0;H2=38.0;H3=50.0;H4=42.0#";
	    
	    SensorReading result = parser.parse(rawData);

	    assertEquals(25.5, result.getAirTemp());
	    assertEquals(18.2, result.getSoilTemp());
	    assertEquals(4, result.getSoilHumidity().size()); // Проверяем количество зон
	    assertEquals(42.0, result.getSoilHumidity().get(3)); // H4
	}
	
	@Test
	void testInCorrectData() {
		String rawData = "T=25.5;H=60.2ZZZ";
        assertThrows(IllegalArgumentException.class, () -> parser.parse(rawData));
	}

}
