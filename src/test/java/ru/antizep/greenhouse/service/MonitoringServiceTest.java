package ru.antizep.greenhouse.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;
import ru.antizep.greenhouse.dto.entity.SensorEntity;
import ru.antizep.greenhouse.dto.repository.SensorReadingRepository;
import ru.antizep.greenhouse.serial.SensorReading;
import ru.antizep.greenhouse.serial.SerialDataParser;
import ru.antizep.greenhouse.serial.command.ReadAllSensorCommand;


@ExtendWith(MockitoExtension.class)
public class MonitoringServiceTest {
	
    @Mock 
    private ArduinoGateway gateway;
    @Mock 
    private SensorReadingRepository repository;
    
    private SerialDataParser parser;
    private MonitoringService service;
    
    
    @BeforeEach
    void setup() {
    	parser = new SerialDataParser();
    	service = new MonitoringService(gateway,parser,repository);
    }
    
    @Test
    void shouldFetchAndSaveDataSuccessfully() {
    	
    	String rawData = "TA=25.5;TS=18.0;H1=40;H2=45;H3=30;H4=35#";
       
        when(gateway.sendAndReceive(any(ReadAllSensorCommand.class))).thenReturn(rawData);
        ArgumentCaptor<SensorEntity> captor = ArgumentCaptor.forClass(SensorEntity.class);
        service.fetchCurrentData();
        verify(repository).save(captor.capture());
        
        SensorEntity savedReading = captor.getValue();
        assertEquals(25.5, savedReading.getAirTemp());
        assertEquals(18.0, savedReading.getSoilTemp());

        Map<GreenhouseZoneEntity, Double> humidity = savedReading.getZoneHumidity();
        assertAll("Проверка влажности по зонам",
            () -> assertEquals(4, humidity.size())
        );
    }

}
