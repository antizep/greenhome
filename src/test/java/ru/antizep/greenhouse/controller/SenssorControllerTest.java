package ru.antizep.greenhouse.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import ru.antizep.greenhouse.dto.ErrorCodeHandbook;
import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;
import ru.antizep.greenhouse.serial.SerialTransport;
import ru.antizep.greenhouse.service.GreenhouseZoneRepository;
@Tag("slow")
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SenssorControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GreenhouseZoneRepository zoneRepository;
    @MockBean
    private SerialTransport fakeSerial;
    
    @BeforeEach
    void setup() {
    	zoneRepository.saveAll(generateZones());
    }
    
    @Test
    void shouldReturnOkWhenCollectDataFromSensors() throws Exception {
    	System.out.println(zoneRepository.findAll());
    	when(fakeSerial.readLine()).thenReturn("TA=252.5;TS=138.2;H1=445.0;H2=328.0;H3=502.0;H4=42.1#");
    	mockMvc.perform(post("/api/monitoring/fetch"))
                .andExpect(status().isOk());
        
    }
    
    @Test
    void shouldReturnIncorrectDataWhenCollectDataFromSensors() throws Exception {
    	System.out.println(zoneRepository.findAll());
    	when(fakeSerial.readLine()).thenReturn("BAD Response#");
    	mockMvc.perform(post("/api/monitoring/fetch").contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isBadGateway())
    			.andExpect(jsonPath(".code").value(ErrorCodeHandbook.UNEXPECTED_RESPONSE.getCode()))
    			.andExpect(jsonPath(".message").exists());
        
    }
    
    @Test
    void shouldReturnZoneNotFound() throws Exception {
    	when(fakeSerial.readLine()).thenReturn("TA=252.5;TS=138.2;H55=445.0#");
    	mockMvc.perform(post("/api/monitoring/fetch"))
    			.andExpect(status().isBadGateway())
    			.andExpect(jsonPath(".code").value(ErrorCodeHandbook.ZONE_NOT_FOUND.getCode()))
    			.andExpect(jsonPath(".message").exists());
        
    }
    
    List<GreenhouseZoneEntity> generateZones(){
    	return List.of(generateZone(1),generateZone(2),generateZone(3),generateZone(4));
    }
    GreenhouseZoneEntity generateZone(int zoneid) {
    	GreenhouseZoneEntity zoneEntity = new GreenhouseZoneEntity();
    	zoneEntity.setDescription("My Zone"+zoneid);
    	zoneEntity.setName("zone"+zoneid);
    	zoneEntity.setSensorPin(20+zoneid);
    	return zoneEntity;
    }
}
