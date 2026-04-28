package ru.antizep.greenhouse.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;
import ru.antizep.greenhouse.dto.entity.HumidityByZone;
import ru.antizep.greenhouse.dto.entity.SensorReadingEntity;
import ru.antizep.greenhouse.exception.ZoneNotFounException;
import ru.antizep.greenhouse.serial.SensorReading;

@ExtendWith(MockitoExtension.class)
public class SensorReadingMapperTest {
	@Mock
	GreenhouseZoneRepository repository;
	SensorReadingMapper mapper;
	
	@BeforeEach
	public void setup() {
		mapper = new SensorReadingMapper(repository);
	}
	
	@Test
	public void sensorReadingToEntityTest() {
		
		SensorReading reading = new SensorReading();
		reading.setAirTemp(24.0);
		reading.setSoilTemp(45.4);
		reading.setSoilHumidity(List.of(3.4,32.4,45.4,11.4));
		
		when(repository.findById(1L)).thenReturn(Optional.of(new GreenhouseZoneEntity(1L,"zone1","грядка под растения",1)));
		when(repository.findById(2L)).thenReturn(Optional.of(new GreenhouseZoneEntity(2L,"zone2","грядка под растения N2",2)));
		when(repository.findById(3L)).thenReturn(Optional.of(new GreenhouseZoneEntity(3L,"zone3","грядка под растения N3",3)));
		when(repository.findById(4L)).thenReturn(Optional.of(new GreenhouseZoneEntity(4L,"zone4","грядка под растения N4",4)));
		
		SensorReadingEntity entity = mapper.mapSensorEntity(reading);
		
		assertEquals(entity.getAirTemp(),24.0);
	    assertEquals(entity.getSoilTemp(),45.4);
	    List<HumidityByZone> humiddity = entity.getHumidityByZone();
	    assertAll("Проверка по зонам",
	    			() -> assertEquals(humiddity.size(),4),
	    			() -> assertEquals(humiddity.get(0).getHumidity(),3.4),
	    			() -> assertEquals(humiddity.get(1).getHumidity(),32.4),
	    			() -> assertEquals(humiddity.get(2).getHumidity(),45.4),
	    			() -> assertEquals(humiddity.get(3).getHumidity(),11.4),
	    			
	    			() -> assertEquals(humiddity.get(0).getZone().getId(),1L),
	    			() -> assertEquals(humiddity.get(1).getZone().getId(),2L),
	    			() -> assertEquals(humiddity.get(2).getZone().getId(),3L),
	    			() -> assertEquals(humiddity.get(3).getZone().getId(),4L)
	    );
	}
	@Test
	public void zoneNotFound() {
		SensorReading reading = new SensorReading();
		reading.setAirTemp(24.0);
		reading.setSoilTemp(45.4);
		reading.setSoilHumidity(List.of(3.4,32.4,45.4,11.4));
		when(repository.findById(any())).thenReturn(Optional.empty());
		assertThrows(ZoneNotFounException.class, ()->mapper.mapSensorEntity(reading));
	}
	
}
