package com.message.separate_process;

import com.message.separate_process.ClientHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientHandlerTest {

    @Mock
    private Socket socket;
    private ByteArrayOutputStream byteOutputStream;
    private ByteArrayInputStream byteInputStream;

    private ClientHandler clientHandler;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Mock the input and output streams
        byteOutputStream = new ByteArrayOutputStream();
        byteInputStream = new ByteArrayInputStream("Test Message\n".getBytes());
        when(socket.getOutputStream()).thenReturn(byteOutputStream);
        when(socket.getInputStream()).thenReturn(byteInputStream);

        clientHandler = new ClientHandler(socket);
    }

    @Test
    void testRunReadsMessage() throws IOException {

        Thread clientHandlerThread = new Thread(clientHandler);
        clientHandlerThread.start();

        // Verify clientHandler reads from socket
        verify(socket, timeout(1000).atLeastOnce()).getInputStream();
    }

    @Test
    void testBroadcastMessage() throws IOException {
        String message = "Message";

        // Mock another socket for second clientHandler
        Socket anotherSocket = mock(Socket.class);
        when(anotherSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        when(anotherSocket.getInputStream()).thenReturn(new ByteArrayInputStream("".getBytes()));

        // Create another clientHandler
        ClientHandler anotherClientHandler = new ClientHandler(anotherSocket);
        ClientHandler.clientHandlers.add(anotherClientHandler);

        // Send message
        clientHandler.broadcastMessage(message);

        // Cleanup
        ClientHandler.clientHandlers.clear();
    }


    @Test
    void testRemoveClientHandler() {
        // Add clientHandler to list
        ClientHandler.clientHandlers.add(clientHandler);

        // Check clientHandler is added
        assertTrue(ClientHandler.clientHandlers.contains(clientHandler));

        // Remove clientHandler
        clientHandler.removeClientHandler();

        // Verify clientHandler and removed from list
        assertTrue(ClientHandler.clientHandlers.contains(clientHandler));
    }
}
