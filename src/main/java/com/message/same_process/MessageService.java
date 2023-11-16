package com.message.same_process;

public interface MessageService {

    /**
     * Sends a message to another player.
     * @param receiver player will receive message.
     * @param message message content to send.
     */
    void sendMessage(MessageService receiver, String message);

    /**
     * Receives a message from another player.
     * @param message message content received.
     */
    void receiveMessage(String message);
}
