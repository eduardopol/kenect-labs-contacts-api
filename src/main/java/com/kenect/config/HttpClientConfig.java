package com.kenect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

/**
 * Configuration class for setting up the HTTP client.
 *
 * This class configures a {@link HttpClient} bean for making HTTP requests. The HTTP client
 * is configured to use HTTP/2 protocol for enhanced performance and efficiency.
 */
@Configuration
public class HttpClientConfig {

    /**
     * Configures and provides an {@link HttpClient} bean.
     *
     * The HTTP client is configured with the following properties:
     *
     *     Protocol version: HTTP/2<
     *
     * @return a configured {@link HttpClient} instance.
     */
    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }
}
