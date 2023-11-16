package com.message.separate_process;

import com.message.separate_process.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.Mockito.*;

class ServerTest {

    @Mock
    private ServerSocket serverSocket;
    @Mock
    private Socket socket;
    private Server server;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Mock the input and output streams
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        when(serverSocket.accept()).thenReturn(socket);
        server = new Server(serverSocket);
    }

    @Test
    void testStartServer() throws IOException {

        when(serverSocket.isClosed()).thenReturn(false, true);

        // Start server
        server.startServer();

        // Verify server accept connection
        verify(serverSocket, times(1)).accept();
    }
}
