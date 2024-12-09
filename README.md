# kenect-labs-contacts-api
Kenect Labs Java API for the Interview Challenge.

This project implements a Spring Boot application to interact with the paginated GET Contacts endpoint. It fetches contacts using three different approaches to demonstrate key concepts in Spring Boot and Java:
- Synchronous Processing: Retrieves contacts page-by-page in a sequential manner.
-	Asynchronous Processing: Uses a custom thread pool to manage multiple asynchronous requests, improving performance and scalability.
-	Reactive Processing: Leverages Spring WebFlux to handle data streams reactively, optimizing resource usage for high-concurrency scenarios.

Key Features and Highlights
-	Pagination Handling: Implements logic to navigate and process all pages from the external contacts API.
-	Caching: Uses Caffeine Cache to store and manage frequently accessed data.
-	Thread Pool Customization: Configures thread pool parameters for asynchronous processing.
-	Reactive Streams: Integrates Spring WebFlux to handle non-blocking data processing.
-	API Documentation: Includes Swagger/OpenAPI for seamless API testing and visualization.

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
2.	API Token and Timeout: The API token is stored in the application.yml file, along with timeout configuration
      ```
      kenect:
         contacts-api:
            token: <replace_this_with_api_token>
            timeout: 10
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
   ```
## Performance Tests

This project includes performance testing scripts using K6, a modern load-testing tool for developers. The provided scripts simulate real-world usage scenarios to benchmark the application’s performance under various conditions.

### Why K6?
-	Easy scripting: Uses JavaScript for test scripts.
-	Powerful metrics: Tracks response time, error rates, throughput, and more.
-	Extensibility: Supports integrations with other tools and custom metrics.

### Installation Guide

- Linux/Mac:
   ```bash
   brew install k6
   ```
- Windows:
  - Download the latest version from the official website: https://k6.io/open-source/

### Running Performance Tests
Run the test with the following command:
   ```bash
   k6 run k6/performance-test.js
   ```

### Test Scenarios
The performanceTest.js script defines scenarios for various API endpoints:


- Endpoint 1: /v1/contacts:
  -   Synchronous processing test.

- Endpoint 2: /v2/contacts:
  -	Asynchronous processing test.

- Endpoint 3: /v3/contacts:
   -	Reactive WebFlux test.

### Customizing the Test
You can modify the performanceTest.js script to adjust:
  -	Number of Virtual Users (VUs): Adjust vus to simulate more or fewer users.
  -	Test Duration: Update duration to run the test longer or shorter.
  -	Endpoints: Change the API endpoints tested by modifying the exec functions.

### Sample Output

K6 will display test metrics in your terminal. Metrics include:
-	http_req_duration: Average response time.
-	http_req_failed: Error rate.
-	vus: Number of virtual users.
-	iterations: Number of completed test iterations.

### Analyzing Results

Metrics to Review:
-	Response Time (http_req_duration):
Ensure the 90th percentile is within acceptable limits (e.g., <2000ms).
-	Error Rate (http_req_failed):
Should ideally be below 1%.
-	Throughput:
Check http_reqs to determine how many requests the system handled.

#### Example output:
```
     http_req_duration..................: avg=163.27ms   min=321µs    med=1.29ms   max=1.68s    p(90)=631.37ms p(95)=655.26ms
       { expected_response:true }.......: avg=163.27ms   min=321µs    med=1.29ms   max=1.68s    p(90)=631.37ms p(95)=655.26ms
     ✓ { scenario:async_endpoint }......: avg=38.89ms    min=365µs    med=1.17ms   max=1.19s    p(90)=3.98ms   p(95)=5.85ms  
     ✓ { scenario:reactive_endpoint }...: avg=530.69ms   min=340.24ms med=511.34ms max=1.68s    p(90)=654.57ms p(95)=675.07ms
     ✓ { scenario:sync_endpoint }.......: avg=33.93ms    min=321µs    med=834µs    max=1s       p(90)=1.86ms   p(95)=3.88ms
```