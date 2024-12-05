# kenect-labs-contacts-api
Kenect Labs Java API for Interview Challenge

This project interacts with the paginated GET Contacts endpoint. It includes functionality to fetch contacts and run a Spring Boot application.

## Prerequisites

Before running the application, ensure the following are installed on your system:

- **Java 21** (or higher)
- **Maven**
- **Git**
- **Bash Shell** for Linux/Mac users
- Any IDE for Windows users (I Don't have a Windows computer to create and test a cmd prompt)

## Setup Linux/Mac Users

1. Clone the repository:
   ```bash
   git clone https://github.com/eduardopol/kenect-labs-contacts-api.git
   cd kenect-labs-contacts-api.git
2. Run the project:
   ```bash
   ./run.sh <replace_this_with_api_token>
3. Call the GET contacts endpoint:
   ```
   curl --location 'http://localhost:8080/kenect-labs/v1/contacts'

## Setup Linux/Mac Users

1. Clone the repository:
   ```bash
   git clone https://github.com/eduardopol/kenect-labs-contacts-api.git
   cd kenect-labs-contacts-api.git
2. Run the project:
   ```
   1 - Import the project in your favorite IDE
   2 - Replace the API token inside the yml file
   3 - Run mvn clean package
   4 - Run the project
3. Call the GET contacts endpoint:
   ```
   curl --location 'http://localhost:8080/kenect-labs/v1/contacts'