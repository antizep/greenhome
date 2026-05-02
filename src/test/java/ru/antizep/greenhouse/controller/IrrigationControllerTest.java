package ru.antizep.greenhouse.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ru.antizep.greenhouse.exception.ArduinoException;
import ru.antizep.greenhouse.service.IrrigationService;
@Tag("slow")
@WebMvcTest(IrrigationController.class)
class IrrigationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IrrigationService irrigationService;

    @Test
    void shouldReturnOkWhenWateringStarted() throws Exception {
        mockMvc.perform(post("/api/irrigation/start")
                .param("zoneId", "1"))
                .andExpect(status().isOk());
        verify(irrigationService).startWatering(1);
    }
    
    @Test
    void shouldReturnBadGatewayWhenArduinoFails() throws Exception {
        doThrow(new ArduinoException("Timeout"))
            .when(irrigationService).startWatering(1);

        mockMvc.perform(post("/api/irrigation/start")
                .param("zoneId", "1"))
                .andExpect(status().isBadGateway());
    }

}
