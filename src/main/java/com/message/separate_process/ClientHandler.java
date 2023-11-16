package com.message.separate_process;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    /**
     * A list of all clientHandlers.
     */
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public String clientUsername;

    /**
     * Constructs ClientHandler for handling communication with client socket.
     *
     * @param socket socket representing client connection.
     */
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = UUID.randomUUID().toString();
            clientHandlers.add(this);
            logger.info("New client connected: " + clientUsername);
        } catch (IOException e) {
            logger.severe("Error setting up client handler: " + e.getMessage());
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Listens for messages from client and broadcasts to other clients.
     */
    @Override
    public void run() {
        try {
            String messageFromClient;
            while ((messageFromClient = bufferedReader.readLine()) != null) {
                broadcastMessage(messageFromClient);
            }
        } catch (IOException e) {
            logger.warning("Error in client handler run method: " + e.getMessage());
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Broadcasts a message to clients except sender.
     *
     * @param messageToSend message to broadcast.
     */
    public void broadcastMessage(String messageToSend) {
        if (messageToSend == null) {
            logger.warning("Received null message, not broadcasting");
            return;
        }
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(this.clientUsername)) {
                    System.out.println("SERVER:Sending to " + clientHandler.clientUsername + ": " + messageToSend);
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    /**
     * Removes this clientHandler from list of active clientHandlers.
     */
    public void removeClientHandler() {
        clientHandlers.remove(this);
    }

    /**
     * Closes resources associated with clientHandler.
     */
    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
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
