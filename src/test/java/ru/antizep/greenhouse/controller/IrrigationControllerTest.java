package ru.antizep.greenhouse.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ru.antizep.greenhouse.dto.ErrorCodeHandbook;
import ru.antizep.greenhouse.dto.entity.GreenhouseZoneEntity;
import ru.antizep.greenhouse.serial.SerialTransport;
import ru.antizep.greenhouse.service.GreenhouseZoneRepository;

@Tag("slow")
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class IrrigationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired 
    private GreenhouseZoneRepository zoneRepository;
    @MockBean
    private SerialTransport fakeSerial;

    @BeforeEach
    void setup() {
    	zoneRepository.save(new  GreenhouseZoneEntity(1, "Zone1", "My zone1", 21));
    }
    
    @Test
    void shouldReturnOkWhenWateringStarted() throws Exception {
    	
    	when(fakeSerial.isOpen()).thenReturn(true);
    	when(fakeSerial.readLine()).thenReturn("OK#");
    	
        mockMvc.perform(post("/api/irrigation/start")
                .param("zoneId", "1"))
                .andExpect(status().isOk());
    }
    
    @Test
    void shouldReturnBadGatewayWhenArduinoFails() throws Exception {
        mockMvc.perform(post("/api/irrigation/start")
                .param("zoneId", "1"))
                .andExpect(status().isBadGateway())
    			.andExpect(jsonPath(".code").value(ErrorCodeHandbook.HARDWARE_NOT_RESPOND.getCode()))
    			.andExpect(jsonPath(".message").exists());
    }

}
