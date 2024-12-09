# kenect-labs-contacts-api
Kenect Labs Java API for the Interview Challenge.

This project implements a Spring Boot application to interact with the paginated GET Contacts endpoint. It fetches contacts using three different approaches to demonstrate key concepts in Spring Boot and Java:
1.	Synchronous Processing: Retrieves contacts page-by-page in a sequential manner.
2.	Asynchronous Processing: Uses a custom thread pool to manage multiple asynchronous requests, improving performance and scalability.
3.	Reactive Processing: Leverages Spring WebFlux to handle data streams reactively, optimizing resource usage for high-concurrency scenarios.

Key Features and Highlights
1.	Pagination Handling: Implements logic to navigate and process all pages from the external contacts API.
2.	Caching: Uses Caffeine Cache to store and manage frequently accessed data.
3.	Thread Pool Customization: Configures thread pool parameters for asynchronous processing.
4.	Reactive Streams: Integrates Spring WebFlux to handle non-blocking data processing.
5.	API Documentation: Includes Swagger/OpenAPI for seamless API testing and visualization.
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
4. Call the GET contacts V2 endpoint for Async processing:
   ```
   curl --location 'http://localhost:8080/kenect-labs/v2/contacts'
5. Call the GET contacts V3 endpoint for Spring WebFlux:
   ```
   curl --location 'http://localhost:8080/kenect-labs/v3/contacts'

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
3. Call the GET contacts endpoint to fetch contacts synchronously from the external API:
   ```
   curl --location 'http://localhost:8080/kenect-labs/v1/contacts'
4. Call the GET contacts V2 endpoint to fetch contacts asynchronously from the external API:
   ```
   curl --location 'http://localhost:8080/kenect-labs/v2/contacts'
5. Call the GET contacts V3 endpoint to fetch contacts using Spring WebFlux from the external API:
   ```
   curl --location 'http://localhost:8080/kenect-labs/v3/contacts'

## Configuration Details

Spring Application Configuration
1.	Cache: The application uses Caffeine as the caching provider
      ```
      spring:
         cache:
            type: caffeine
2.	API Token: The API token is stored in the application.yml file
      ```
      kenect:
         contacts-api:
            token: <replace_this_with_api_token>
3.  Thread Pool Configuration for Asynchronous Processing
      ```
      kenect:
         async:
            core-pool-size: 10
            max-pool-size: 20
            queue-capacity: 100

## Notes
   1.	Replace the API token in application.yml with your actual token before running the project.

   2.	Swagger documentation is available at:
   ```
   http://localhost:8080/kenect-labs/swagger-ui.html