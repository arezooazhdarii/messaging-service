# Java Socket Messaging Application

This application demonstrates a simple client-server communication using Java sockets. The server listens for incoming connections and each client sends messages which are then broadcast to all connected clients by the server.

## Project Structure

- `src/main/java/com/message/Client.java`: Client class responsible for initiating a connection and communicating with the server.
- `src/main/java/com/message/ClientApplication.java`: Executable class for running the client.
- `src/main/java/com/message/ClientHandler.java`: Handles the communication between the server and one connected client.
- `src/main/java/com/message/Server.java`: Server class that waits for and accepts incoming client connections.
- `src/main/java/com/message/ServerApplication.java`: Executable class for running the server.
- `src/test/java/com/message/ClientTest.java`: Unit tests for the Client class.
- `src/test/java/com/message/ServerTest.java`: Unit tests for the Server class.
- `src/test/java/com/message/ClientHandlerTest.java`: Unit tests for the ClientHandler class.
- `runApplication.sh`: Script to compile and launch the server and client applications.

## Prerequisites

- Java JDK 11 or higher must be installed on your machine.
- Maven 3.6.0 or higher for managing dependencies and running the application.

## Setup and Execution

1. **Compile the application**: Navigate to the project's root directory and use Maven to compile the application:

    ```sh
    mvn clean compile
    ```

2. **Run the server**: Start the server application with the following command:

    ```sh
    java -cp target/classes com.message.ServerApplication
    ```

3. **Run the client**: In a new terminal window, start the client application with the following command:

    ```sh
    java -cp target/classes com.message.ClientApplication
    ```

4. **Execute tests**: Run the unit tests to ensure the application is functioning correctly:

    ```sh
    mvn test
    ```

5. **Running the Shell Script**: Before executing the `runApplication.sh`, ensure it has the necessary execution permissions:

    ```sh
    chmod +x runApplication.sh
    ./runApplication.sh
    ```

   This script will compile, package, and run the server and client programs.

## Interacting with the Application

After launching the client and server, the client will automatically send a message to the server. The server will respond and the client will continue to send messages until the predetermined limit is reached.

## Documentation

Each class and method in the project is documented with JavaDoc to explain their purpose and usage. You can generate the full JavaDoc documentation by running:

```sh
mvn javadoc:javadoc
