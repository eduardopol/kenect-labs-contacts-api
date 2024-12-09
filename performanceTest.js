import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter, Trend, Rate } from 'k6/metrics';

export const options = {
    scenarios: {
        sync_endpoint: {
            executor: 'constant-vus',
            vus: 50,
            duration: '30s',
            exec: 'testSyncEndpoint',
        },
        async_endpoint: {
            executor: 'constant-vus',
            startTime: '40s',
            vus: 50,
            duration: '30s',
            exec: 'testAsyncEndpoint',
        },
        reactive_endpoint: {
            executor: 'constant-vus',
            startTime: '80s',
            vus: 50,
            duration: '30s',
            exec: 'testReactiveEndpoint',
        },
    },
    thresholds: {
        'http_req_duration{scenario:sync_endpoint}': ['p(90)<2000'], // 90% of requests < 2s
        'http_req_duration{scenario:async_endpoint}': ['p(90)<2000'],
        'http_req_duration{scenario:reactive_endpoint}': ['p(90)<2000'],
        'http_req_failed': ['rate<0.01'], // Less than 1% errors
    },
};

// Metrics
const syncEndpointCounter = new Counter('sync_endpoint_calls');
const asyncEndpointCounter = new Counter('async_endpoint_calls');
const reactiveEndpointCounter = new Counter('reactive_endpoint_calls');

const syncEndpointErrorCounter = new Counter('sync_endpoint_errors');
const asyncEndpointErrorCounter = new Counter('async_endpoint_errors');
const reactiveEndpointErrorCounter = new Counter('reactive_endpoint_errors');

const syncTrend = new Trend('sync_response_time');
const asyncTrend = new Trend('async_response_time');
const reactiveTrend = new Trend('reactive_response_time');

const successRate = new Rate('http_req_successful');
const failureCounter = new Counter('http_req_failed_total');

// Utility function to handle repetitive logic
function testEndpointHelper(url, trend, endpointName, counter, errorCounter) {
    const res = http.get(url);
    const success = check(res, {
        'status is 200': (r) => r.status === 200,
    });

    // Increment scenario-specific counter
    counter.add(1);

    // Record response time and success rate
    trend.add(res.timings.duration);
    successRate.add(success);

    if (!success) {
        failureCounter.add(1);
        errorCounter.add(1); // Increment error counter for this scenario
        console.error(`[${endpointName}] Request failed with status: ${res.status}`);
    }
    sleep(1); // Simulate user think time
}

// Test functions for each scenario
export function testSyncEndpoint() {
    testEndpointHelper(
        'http://localhost:8080/kenect-labs/v1/contacts',
        syncTrend,
        'Sync Endpoint',
        syncEndpointCounter,
        syncEndpointErrorCounter
    );
}

export function testAsyncEndpoint() {
    testEndpointHelper(
        'http://localhost:8080/kenect-labs/v2/contacts',
        asyncTrend,
        'Async Endpoint',
        asyncEndpointCounter,
        asyncEndpointErrorCounter
    );
}

export function testReactiveEndpoint() {
    testEndpointHelper(
        'http://localhost:8080/kenect-labs/v3/contacts',
        reactiveTrend,
        'Reactive Endpoint',
        reactiveEndpointCounter,
        reactiveEndpointErrorCounter
    );
}