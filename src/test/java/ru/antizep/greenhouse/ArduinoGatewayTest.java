package ru.antizep.greenhouse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.antizep.greenhouse.ArduinoGateway;
import ru.antizep.greenhouse.SerialTransport;
import ru.antizep.greenhouse.serial.command.ArduinoCommand;
import ru.antizep.greenhouse.serial.command.GetAllCommand;
import ru.antizep.greenhouse.serial.command.PumpOnRequest;

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

        ArduinoCommand command = new GetAllCommand();
        String expectedResponse = "TA=25.5;H1=40#";
        
        when(transport.isOpen()).thenReturn(true);
        when(transport.readLine()).thenReturn(expectedResponse);


        String actualResponse = gateway.sendAndReceive(command);

        assertEquals(expectedResponse, actualResponse);
        verify(transport).write(command.getCommandString());
    }

    @Test
    void shouldConnectIfPortIsClosed() {

        when(transport.isOpen()).thenReturn(false);
        when(transport.readLine()).thenReturn("OK#");
        
        gateway.sendAndReceive(new PumpOnRequest(1));

        verify(transport).connect();
    }
    
    @Test
    void shouldRetryIfFirstResponseIsEmpty() {
       
        ArduinoCommand command = new GetAllCommand();
        when(transport.isOpen()).thenReturn(true);
       
        when(transport.readLine()).thenReturn("").thenReturn("TA=25.5#");

 
        String response = gateway.sendAndReceive(command);

        assertEquals("TA=25.5#", response);
        verify(transport, times(2)).write(command.getCommandString());
    }
}