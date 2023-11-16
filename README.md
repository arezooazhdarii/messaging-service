# Java Socket Messaging Application

## Overview

This Java Socket Messaging Application demonstrates two communication scenarios for players: same-process and separate-process. Players exchange messages, and each player responds by appending a message count to the received message. The application can gracefully terminate after the initiator sends and receives 10 messages.

## Project Structure

The project structure is organized into two main packages, one for each communication scenario.

### Same-Process Communication

- `src/main/java/com/message/same_process/`:
   - `MainApplication.java`: Entry point for the same-process communication scenario.
   - `Initiator.java`: Represents the initiating player, responsible for starting communication.
   - `Receiver.java`: Represents the receiving player, which responds to messages.
   - `MessageService.java`: Abstract class that encapsulates common player functionality.
   - `runSameProcess.sh`: Shell script to compile and run communication scenarios.


### Separate-Process Communication

- `src/main/java/com/message/separate_process/`:
   - `ServerApplication.java`: Entry point for the server in the separate-process scenario.
   - `ClientApplication.java`: Entry point for the client in the separate-process scenario.
   - `ClientHandler.java`: Manages client communication on the server side.
   - `Server.java`: Handles server functionality, such as accepting client connections.

- `pom.xml`: Contains Maven build configurations.
- `runSeparateProcess.sh`: Shell script to compile and run both communication scenarios.

## Prerequisites

Before running the application, ensure you have the following prerequisites installed:

- Java JDK 11 or higher.
- Maven 3.6.0 or higher.

## How to run

Follow these steps to set up and run the application:

### 1. Testing the application

To run test cases of the project execute the following command:

```bash
  mvn test
```

### 3. Run the Shell Script

#### 3.1 Same-Process Scenario
To start the same-process communication scenario, execute the following command:

```sh
./runSameProcess.sh
```
This command initializes both the initiator and receiver within the same Java process, allowing them to communicate directly.


#### 3.2 Separate-Process Scenario
To run the separate-process communication scenario, follow these steps:

Make the runSeparateProcess.sh script executable:
```sh
chmod +x runSeparateProcess.sh
```

Execute the script:

```sh
./runSeparateProcess.sh
```

The script will perform the following actions:

- Clean and package the project using Maven.
- Start the server in the background.
- Start the client and connect it to the server.

## Interaction
In both communication scenarios:

- The initiator, designated as the starting player, sends an initial message to the receiver.
- The receiver responds to received messages by appending a message count.
- This message exchange continues until the initiator has sent and received 10 messages, serving as the stop condition.

## Additional Notes

- The application is configured to use port 8090 for socket communication.
- Ensure the port is free or change the port in the source code if needed.
