package com.message.separate_process;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private final ServerSocket serverSocket;

    /**
     * Constructs new Server with ServerSocket.
     *
     * @param serverSocket server socket listens for client connections.
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Starts server and listen for client connections. For each connection, it starts new thread with a ClientHandler.
     * Accepting new connections until server socket closed.
     */
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            logger.severe("Error in server: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
