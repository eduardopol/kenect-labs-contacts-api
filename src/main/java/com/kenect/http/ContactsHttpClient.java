package com.kenect.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * HTTP client for interacting with the external Contacts API.
 * Handles requests to fetch paginated contact information.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContactsHttpClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${kenect.contacts-api.url:https://api.example.com}")
    private String apiUrl;

    @Value("${kenect.contacts-api.token}")
    private String apiToken;

    @Value("${kenect.contacts-api.timeout:10}")
    private Integer httpTimeout;

    /**
     * Fetches a single page of contacts from the external API.
     *
     * @param page the page number to fetch.
     * @return a {@link ContactListDto} object containing the contacts and pagination details.
     */
    public ContactListDto fetchContactsPage(Integer page) {
        if (Objects.isNull(page) || page < 1 || page > 1000) {
            throw new IllegalArgumentException("Page number must be greater than 0 and less than 1000");
        }

        String url = String.format("%s?page=%d&pageSize=%d", apiUrl, page, 20);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(httpTimeout))
                .header("Authorization", "Bearer " + apiToken)
                .GET()
                .build();

        try {
            log.debug("Making request to GET contacts page {}", page);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("Request to {} return response code {}", url, response.statusCode());

            if (response.statusCode() == 200) {
                List<ContactDto> contactDtos = objectMapper.readValue(response.body(), new TypeReference<>() {});

                return ContactListDto.builder()
                        .contacts(contactDtos)
                        .currentPage(getHeaderByHeaderName(response, "current-page").orElse(page))
                        .totalContacts(getHeaderByHeaderName(response, "total-count").orElse(contactDtos.size()))
                        .totalPages(getHeaderByHeaderName(response, "total-pages").orElse(page))
                        .build();
            } else if (response.statusCode() >= 400 && response.statusCode() < 500) {
                log.warn("Client error: {} for URL {}", response.statusCode(), url);
                throw new RuntimeException("Failed to fetch contacts from the API. Client error: " + response.statusCode());
            } else if (response.statusCode() >= 500) {
                log.error("Server error: {} for URL {}", response.statusCode(), url);
                throw new RuntimeException("Failed to fetch contacts from the API. Server error: " + response.statusCode());
            } else {
                throw new RuntimeException("Failed to fetch contacts from the API");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch contacts from the API", e);
        }
    }

    /**
     * Retrieves the value of a specific HTTP header from the response.
     *
     * @param response the {@link HttpResponse} object containing headers.
     * @param header   the name of the header to retrieve.
     * @return an {@link Optional} containing the value of the header, if present.
     */
    private Optional<Integer> getHeaderByHeaderName(HttpResponse<String> response, String header) {
        return response.headers()
                .firstValue(header)
                .map(Integer::valueOf);
    }

}
