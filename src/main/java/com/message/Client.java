package com.message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    private int messageCount = 0;
    private boolean isInitiator;

    public Client(Socket socket, String username,boolean isInitiator) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            this.isInitiator = isInitiator;
            sendMessage(username); // Send the username as the first message after setup
            if (isInitiator) {
                sendInitialMessage();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void startCommunication() {
        if (isInitiator) {
            sendInitialMessage();
        }
        listenForMessage();
    }

    private void sendInitialMessage() {
        // This method is only called by the initiator to start the communication
        sendMessage("Hello");
    }

    public void sendMessage(String message) {
        try {
            String logMessage = String.format("[%s - %s]: Sending message: %s",
                    username,
                    isInitiator ? "Initiator" : "Receiver",
                    message);
            System.out.println(logMessage);
            bufferedWriter.write(username + ": " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

//            Scanner scanner = new Scanner(System.in);
//            while (socket.isConnected()) {
//                String messageToSend = scanner.nextLine();
//                bufferedWriter.write("username :" + messageToSend);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//            }

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            public void run() {
                String messageFromClient;
                while (socket.isConnected() && (!isInitiator || messageCount < 10)) {
                    try {
                        messageFromClient = bufferedReader.readLine();
                        System.out.println(messageFromClient);
                        handleMessage(messageFromClient);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
                if (isInitiator) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
        }).start();
    }

    private void handleMessage(String message) {
        // Increment the message count
        messageCount++;

        // Send a new message with this client's message count appended
        if (messageCount <= 10) {
            sendMessage("Message " + messageCount);
        } else {
            // If the initiator has sent and received 10 messages, close the connection
            if (isInitiator) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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

    public static void main(String[] args) throws IOException {
        String username;
        boolean isInitiator;

        if (args.length >= 2) {
            username = args[0];
            isInitiator = args[1].equalsIgnoreCase("initiator");
        } else {
            // Default values for quick testing
            username = "bob"; // or "Bob" for the receiver
            isInitiator = false; // set to false for the receiver
        }

        Socket socket = new Socket("localhost", 8090);
        Client client = new Client(socket, username, isInitiator);
        client.startCommunication();
    }
}
