#!/bin/bash

# Navigate to the project root directory
cd "$(dirname "$0")"

# Compile and package the project using Maven
mvn clean package

# Check if the compilation was successful
if [ $? -eq 0 ]; then
    # Run the server
    java -cp target/socket-1.0-SNAPSHOT.jar com.message.ServerApplication &

    # Give the server some time to start
    sleep 5

    # Run the client
    java -cp target/socket-1.0-SNAPSHOT.jar com.message.ClientApplication
else
    echo "Build failed, exiting."
fi
