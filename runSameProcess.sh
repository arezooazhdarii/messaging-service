Copy code
#!/bin/bash

# Navigate to the project root directory
cd "$(dirname "$0")"

# Compile and package the project using Maven
mvn clean package

# Check if the compilation was successful
if [ $? -eq 0 ]; then
    # Run the same-process scenario
    mvn exec:java -Dexec.mainClass="com.message.same_process.MainApplication"
else
    echo "Build failed, exiting."
fi