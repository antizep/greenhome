package ru.antizep.greenhouse.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.dto.entity.SensorReadingEntity;
import ru.antizep.greenhouse.dto.repository.SensorReadingRepository;
import ru.antizep.greenhouse.exception.InvalidProtocolException;
import ru.antizep.greenhouse.serial.SensorReading;
import ru.antizep.greenhouse.serial.SerialDataParser;
import ru.antizep.greenhouse.serial.command.ReadAllSensorCommand;


@ExtendWith(MockitoExtension.class)
public class MonitoringServiceTest {
	
    @Mock 
    private ArduinoGateway gateway;
    @Mock 
    private SensorReadingRepository repository;
    @Mock
    private SensorReadingMapper sensorReadingMapper;
    
    private SerialDataParser parser;
    private MonitoringService service;
    
    
    @BeforeEach
    void setup() {
    	parser = new SerialDataParser();
    	service = new MonitoringService(gateway,parser,repository,sensorReadingMapper);
    }
    
    @Test
    void shouldFetchAndSaveDataSuccessfully() throws InvalidProtocolException {
    	
    	String rawData = "TA=25.5;TS=18.0;H1=40;H2=45;H3=30;H4=35#";
       
        when(gateway.sendAndReceive(any(ReadAllSensorCommand.class))).thenReturn(rawData);
        SensorReadingEntity expectedEntity = new SensorReadingEntity();
        when(sensorReadingMapper.mapSensorEntity(any(SensorReading.class))).thenReturn(expectedEntity);
        
        service.fetchCurrentData();
        verify(repository).save(expectedEntity);
        
    }

    @Test
    void shouldFetchAndSaveDataBadParse() throws InvalidProtocolException {
    	
    	String rawData = "#A=25.5;TS=18.0;H1=40;H2=45;H3=30;H4=35#";
       
        when(gateway.sendAndReceive(any(ReadAllSensorCommand.class))).thenReturn(rawData);
        
        assertThrows(InvalidProtocolException.class, () -> service.fetchCurrentData());
        
    }
}
