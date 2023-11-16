package com.message.separate_process;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class Client {

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private int messageCount = 0;
    private boolean isInitiator;

    /**
     * Constructs a new Client.
     *
     * @param socket      socket for communication
     * @param username    the client
     * @param isInitiator client is the initiator
     */
    public Client(Socket socket, String username, boolean isInitiator) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            this.isInitiator = isInitiator;
            logger.info("Client initialized: " + username);
        } catch (IOException e) {
            logger.severe("Error setting up client: " + e.getMessage());
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Starts communication for client
     */
    public void startCommunication() {
        if (isInitiator) {
            sendInitialMessage();
        }
        listenForMessage();
    }

    /**
     * Sends an initial message
     */
    private void sendInitialMessage() {
        sendMessage("Hello");
    }

    /**
     * Sends message to server.
     *
     * @param message message to sent
     */
    public void sendMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Listens for messages.
     */
    public void listenForMessage() {
        new Thread(() -> {
            String messageFromClient;
            while (socket.isConnected() && (messageCount <= 10)) {
                try {
                    messageFromClient = bufferedReader.readLine();
                    System.out.println(username + " -> " + messageFromClient);
                    handleMessage(messageFromClient);
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }
            }
            if (isInitiator) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }).start();
    }

    /**
     * Handles received message.
     *
     * @param message message receive from server
     */
    private void handleMessage(String message) {
        messageCount++;
        if (messageCount <= 10) {
            sendMessage(message + messageCount);
        } else {
            if (isInitiator) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    /**
     * Closes all resources (socket).
     */
    void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
