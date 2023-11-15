package com.message;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private int messageCount = 0;
    private boolean isInitiator;

    public Client(Socket socket, String username, boolean isInitiator) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            this.isInitiator = isInitiator;
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
        sendMessage("Hello");
    }

    public void sendMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

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

        Socket socketInitiator = new Socket("localhost", 8090);
        Client clientInitiator = new Client(socketInitiator, "initiator", true);
        clientInitiator.startCommunication();

        Socket socketReceiver = new Socket("localhost", 8090);
        Client clientReceiver = new Client(socketReceiver, "receiver", false);
        clientReceiver.startCommunication();

    }
}
