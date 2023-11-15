# Java Socket Messaging Application

This project is a Java-based messaging application using socket programming. It consists of a `Server` class that handles incoming client connections, a `Client` class that initiates and handles communication, and a `ClientHandler` class that manages client interactions.

## Project Structure

- `src/main/java/com/message/ServerApplication.java`: The server program that waits for clients to connect.
- `src/main/java/com/message/ClientApplication.java`: The client program that connects to the server and communicates messages.
- `src/main/java/com/message/ClientHandler.java`: Handles client communication after connecting to the server.
- `pom.xml`: Maven build configuration file.
- `runApplication.sh`: Shell script to compile and run the server and client programs.

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6.0 or higher

## how to run

Follow these steps to set up and run the application:

### 1. Testing the application

To run test cases of the project execute the following command:

```bash
  mvn test
```


### 3. Run the Shell Script

Execute the `runApplication.sh` script to compile and run the server and client.

```sh
./runApplication.sh
```

The script will perform the following actions:

- Clean and package the project using Maven.
- Start the server in the background.
- Start the client and connect it to the server.

### 4. Interacting with the Application

The client will automatically send an initial message to the server. The server will respond, and the client will continue the conversation until the message count reaches 10.

## Documentation

Each class within the project is documented with JavaDoc comments to explain its purpose and methods. Further documentation can be found inline with the code, describing key decisions and functionality.

## Additional Notes

- The application is configured to use port 8090 for socket communication.
- Ensure the port is free or change the port in the source code if needed.
