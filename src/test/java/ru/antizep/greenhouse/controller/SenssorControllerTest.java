package ru.antizep.greenhouse.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.antizep.greenhouse.service.MonitoringService;

@WebMvcTest(MonitoringController.class)
public class SenssorControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitoringService sensorService;
    
    @Test
    void shouldReturnOkWhenCollectDataFromSensors() throws Exception {
        mockMvc.perform(post("/api/monitoring/fetch"))
                .andExpect(status().isOk());
        verify(sensorService).fetchCurrentData();
    }
    
}
