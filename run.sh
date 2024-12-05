#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Check if the token is provided
if [ -z "$1" ]; then
  echo "Usage: $0 <CONTACTS_API_TOKEN>"
  exit 1
fi

# Set the token as an environment variable
export KENECT_CONTACTS_API_TOKEN="$1"

# Run Maven tests
echo "Running Maven tests..."
mvn clean test

# Start the Spring Boot application
echo "Starting the Spring Boot application..."
mvn spring-boot:run
