package ru.antizep.greenhouse.serial;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.SerialTransport;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArduinoGatewayTest {

    @Mock
    private SerialTransport transport;

    private ArduinoGateway gateway;

    @BeforeEach
    void setUp() {
        gateway = new ArduinoGateway(transport);
    }

    @Test
    void shouldSendAndReceiveSuccessfully() {

        String command = "GET:ALL#";
        String expectedResponse = "TA=25.5;H1=40#";
        
        when(transport.isOpen()).thenReturn(true);
        when(transport.readLine()).thenReturn(expectedResponse);


        String actualResponse = gateway.sendAndReceive(command);

        assertEquals(expectedResponse, actualResponse);
        verify(transport).write(command); // Проверяем, что в транспорт ушла именно наша команда
    }

    @Test
    void shouldConnectIfPortIsClosed() {

        when(transport.isOpen()).thenReturn(false);
        when(transport.readLine()).thenReturn("OK#");

        gateway.sendAndReceive("PUMP:ON#");

        verify(transport).connect(); // Проверяем, что шлюз вызвал коннект
    }
    
    @Test
    void shouldRetryIfFirstResponseIsEmpty() {
        // GIVEN
        String command = "GET:ALL#";
        when(transport.isOpen()).thenReturn(true);
        // Первый вызов - пусто, второй - нормальный ответ
        when(transport.readLine()).thenReturn("").thenReturn("TA=25.5#");

        // WHEN
        String response = gateway.sendAndReceive(command);

        // THEN
        assertEquals("TA=25.5#", response);
        verify(transport, times(2)).write(command); // Проверяем, что было 2 попытки
    }
}