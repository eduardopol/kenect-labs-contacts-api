package com.kenect.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * Reactive HTTP client for interacting with the external Contacts API.
 * Handles requests to fetch paginated contact information.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveContactsHttpClient {

    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper objectMapper;

    @Value("${kenect.contacts-api.url:https://api.example.com}")
    private String apiUrl;

    @Value("${kenect.contacts-api.token:defaultToken}")
    private String apiToken;

    /**
     * Fetches a single page of contacts from the external API reactively.
     *
     * @param page the page number to fetch.
     * @return a Mono containing {@link ContactListDto}.
     */
    public Mono<ContactListDto> fetchContactsPage(Integer page) {
        if (Objects.isNull(page) || page < 1 || page > 1000) {
            return Mono.error(new IllegalArgumentException("Page number must be greater than 0 and less than 1000"));
        }

        return webClientBuilder.baseUrl(apiUrl).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("page", page)
                        .queryParam("pageSize", 20)
                        .build())
                .header("Authorization", "Bearer " + apiToken)
                .retrieve()
                .toEntity(String.class)
                .flatMap(response -> {
                    log.debug("Request to page {} returned response code {}", page, response.getStatusCode());

                    if (!response.getHeaders().containsKey("Content-Type") ||
                            !Objects.requireNonNull(response.getHeaders().getFirst("Content-Type")).contains("application/json")) {
                        return Mono.error(new IllegalArgumentException("Unexpected Content-Type header"));
                    }

                    if (response.getStatusCode().is2xxSuccessful()) {
                        return processResponse(response.getBody(), response.getHeaders().toSingleValueMap(), page);
                    } else if (response.getStatusCode().is4xxClientError()) {
                        log.warn("Client error: {}", response.getStatusCode());
                        return Mono.error(new RuntimeException("Client error: " + response.getStatusCode()));
                    } else if (response.getStatusCode().is5xxServerError()) {
                        log.error("Server error: {}", response.getStatusCode());
                        return Mono.error(new RuntimeException("Server error: " + response.getStatusCode()));
                    } else {
                        return Mono.error(new RuntimeException("Failed to fetch contacts from the API"));
                    }
                })
                .doOnError(error -> log.error("Error fetching contacts page {}: {}", page, error.getMessage()));
    }

    /**
     * Processes the HTTP response body and headers to construct a ContactListDto.
     *
     * @param responseBody the HTTP response body as a string.
     * @param headers      the HTTP response headers.
     * @param defaultPage  the default page number to use if the header is missing.
     * @return a Mono containing the constructed {@link ContactListDto}.
     */
    private Mono<ContactListDto> processResponse(String responseBody, java.util.Map<String, String> headers, int defaultPage) {
        try {
            List<ContactDto> contactDtos = objectMapper.readValue(responseBody, new TypeReference<>() {});

            ContactListDto contactListDto = ContactListDto.builder()
                    .contacts(contactDtos)
                    .currentPage(parseHeader(headers.get("current-page"), defaultPage))
                    .totalContacts(parseHeader(headers.get("total-count"), contactDtos.size()))
                    .totalPages(parseHeader(headers.get("total-pages"), defaultPage))
                    .build();

            return Mono.just(contactListDto);
        } catch (Exception e) {
            log.error("Failed to parse response body", e);
            return Mono.error(new RuntimeException("Failed to parse contacts API response", e));
        }
    }

    /**
     * Parses a header value into an integer with a fallback default value.
     *
     * @param headerValue the header value as a string.
     * @param defaultValue the fallback default value.
     * @return the parsed integer value, or the default value if parsing fails.
     */
    private int parseHeader(String headerValue, int defaultValue) {
        try {
            return headerValue != null ? Integer.parseInt(headerValue) : defaultValue;
        } catch (NumberFormatException e) {
            log.warn("Failed to parse header value {}: {}", headerValue, e.getMessage());
            return defaultValue;
        }
    }
}