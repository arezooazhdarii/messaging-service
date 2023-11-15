package com.message;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerApplication {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8090);
        Server server = new Server(serverSocket);
        server.startServer();
    }

}
