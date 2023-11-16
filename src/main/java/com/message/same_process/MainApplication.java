package com.message.same_process;

public class MainApplication {
    public static void main(String[] args) {
        Initiator initiator = new Initiator();
        Receiver receiver = new Receiver();

        String message = "Hello";
        for (int i = 0; i < 10; i++) {
            receiver.sendMessage(initiator, message);
            message = initiator.getLastReceivedMessage(); // Update message from initiator

            initiator.sendMessage(receiver, message);
            message = receiver.getLastReceivedMessage(); // Update message from receiver
        }

        System.out.println("Message exchange complete.");
    }
}


