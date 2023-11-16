package com.message.separate_process;

import java.io.IOException;
import java.net.Socket;

public class ClientApplication {
    public static void main(String[] args) throws IOException {

        // First client setup
        Socket socketInitiator = new Socket("localhost", 8090);
        Client clientInitiator = new Client(socketInitiator, "initiator", true);
        clientInitiator.startCommunication();

        // Second client setup
        Socket socketReceiver = new Socket("localhost", 8090);
        Client clientReceiver = new Client(socketReceiver, "receiver", false);
        clientReceiver.startCommunication();
    }
}
