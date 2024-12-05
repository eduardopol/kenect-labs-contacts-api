package com.kenect.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import com.kenect.factory.ObjectMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContactsHttpClientTest {

    private ContactsHttpClient contactsHttpClient;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = ObjectMapperFactory.createObjectMapper();
        contactsHttpClient = new ContactsHttpClient(httpClient, objectMapper);
    }

    @Test
    void testFetchContactsPage_Success() throws Exception {
        // Arrange
        List<ContactDto> mockContacts = List.of(
                ContactDto.builder().id(1L).name("John Doe").email("john.doe@example.com").build(),
                ContactDto.builder().id(2L).name("Jane Doe").email("jane.doe@example.com").build()
        );

        String mockBody = objectMapper.writeValueAsString(mockContacts);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(mockBody);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        ContactListDto result = contactsHttpClient.fetchContactsPage(1);

        assertNotNull(result);
        assertEquals(2, result.getContacts().size());
        assertEquals("John Doe", result.getContacts().get(0).getName());
        verify(httpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void testFetchContactsPage_Failure() throws Exception {
        // Arrange
        when(httpResponse.statusCode()).thenReturn(500);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> contactsHttpClient.fetchContactsPage(1));
        assertEquals("Failed to fetch contacts from the API", exception.getMessage());
        verify(httpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
}