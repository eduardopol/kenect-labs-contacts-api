package com.kenect.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import com.kenect.factory.ObjectMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Component
public class ContactsHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(ContactsHttpClient.class);

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${kenect.contacts-api.url}")
    private String apiUrl;

    @Value("${kenect.contacts-api.token}")
    private String apiToken;

    public ContactsHttpClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = ObjectMapperFactory.createObjectMapper();
    }

    /**
     * Fetch a single page of contacts from the external API.
     *
     * @param page the page number to fetch
     * @return a ContactListDto object, with List of Contacts and pagination info
     */
    public ContactListDto fetchContactsPage(Integer page) {
        String url = String.format("%s?page=%d&pageSize=%d", apiUrl, page, 20);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiToken)
                .GET()
                .build();

        try {
            logger.debug("Making request to GET contacts page {}", page);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.debug("Request to {} return response code {}", url, response.statusCode());

            if (response.statusCode() == 200) {
                List<ContactDto> contactDtos = objectMapper.readValue(response.body(), new TypeReference<>() {});

                return ContactListDto.builder()
                        .contacts(contactDtos)
                        .currentPage(getHeaderByHeaderName(response, "current-page").orElse(page))
                        .totalContacts(getHeaderByHeaderName(response, "total-count").orElse(contactDtos.size()))
                        .totalPages(getHeaderByHeaderName(response, "total-pages").orElse(page))
                        .build();
            } else {
                throw new RuntimeException("Failed to fetch contacts from the API");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch contacts from the API", e);
        }
    }

    private Optional<Integer> getHeaderByHeaderName(HttpResponse<String> response, String header) {
        return response.headers()
                .firstValue(header)
                .map(Integer::valueOf);
    }

}
