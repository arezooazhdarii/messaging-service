package com.message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public String clientUsername;

    public ClientHandler(Socket socket,String clientUsername) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // This is blocking, move it to the run method where you handle different message types
//             this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            // Don't broadcast yet, wait until you have the username
            // broadcastMessage("SERVER:" + clientUsername + "has entered the chat");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        try {
            // First message should be the username
            clientUsername = bufferedReader.readLine();
            System.out.println("a new client has connected!" + clientUsername);

            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");

            // Rest of the message handling
            String messageFromClient;
            while ((messageFromClient = bufferedReader.readLine()) != null) {
//                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void broadcastMessage(String messageToSend) {
        if (messageToSend == null) {
            System.out.println("Received null message, not broadcasting");
            return; // Exit the method if the message is null
        }
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(this.clientUsername)) {
                    // Here, you should send only the new message part, not the entire received message
                    // For now, let's log the intended message to send
                    System.out.println("Sending to " + clientHandler.clientUsername + ": " + messageToSend);
                    // You might want to extract just the relevant part of the message here
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER:" + clientUsername + "has left the chat");
    }

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
