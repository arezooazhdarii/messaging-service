package com.message.separate_process;

import com.message.separate_process.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ClientTest {

    @Mock
    private Socket socket;
    private ByteArrayOutputStream byteOutputStream;
    private ByteArrayInputStream byteInputStream;

    private Client client;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Creating stream to simulate socket I/O
        byteOutputStream = new ByteArrayOutputStream();
        byteInputStream = new ByteArrayInputStream("Test Response\n".getBytes());

        // Mocking the socket's output and input streams
        when(socket.getOutputStream()).thenReturn(byteOutputStream);
        when(socket.getInputStream()).thenReturn(byteInputStream);

        // Initializing the client
        client = new Client(socket, "testClient", true);
    }

    @Test
    void testSendMessage() {
        String testMessage = "Hello";
        client.sendMessage(testMessage);

        // Verify that the message was correctly written to the output stream
        String sentMessage = byteOutputStream.toString();
        assertTrue(sentMessage.contains(testMessage));
    }

    @Test
    void testSendInitialMessageAsInitiator(){
        // client as an initiator
        Client initiatorClient = new Client(socket, "initiator", true);

        initiatorClient.startCommunication();

        String sentMessage = byteOutputStream.toString();
        assertTrue(sentMessage.contains("Hello"));
    }

    @Test
    void testReceiverMessageNotInitiator(){
        // client as a reciver
        Client nonInitiatorClient = new Client(socket, "reciver", false);

        nonInitiatorClient.startCommunication();

        String sentMessage = byteOutputStream.toString();
        assertTrue(sentMessage.isEmpty());
    }

}
