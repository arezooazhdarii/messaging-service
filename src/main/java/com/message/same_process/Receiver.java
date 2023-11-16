package com.message.same_process;

public class Receiver implements MessageService {
    private int messageCount = 0;
    private String lastReceivedMessage = "";

    @Override
    public void sendMessage(MessageService receiver, String message) {
        receiver.receiveMessage(message + (++messageCount));
    }

    @Override
    public void receiveMessage(String message) {
        lastReceivedMessage = message;
        System.out.println("Receiver received: " + message);
    }

    /**
     * Retrieves last message received by player.
     * @return last message received.
     */
    public String getLastReceivedMessage() {
        return lastReceivedMessage;
    }
}


