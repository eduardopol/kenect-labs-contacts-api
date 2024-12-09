package com.kenect.controller;

import com.kenect.model.Contact;
import com.kenect.service.impl.ReactiveContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Arrays;

@WebFluxTest(ReactiveContactController.class)
class ReactiveContactControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ReactiveContactServiceImpl contactService;

    @Test
    void testGetAllContacts() {
        Contact contact1 = new Contact(1L, "John Doe", "john@example.com", LocalDateTime.now(), LocalDateTime.now());
        Contact contact2 = new Contact(2L, "Jane Smith", "jane@example.com", LocalDateTime.now(), LocalDateTime.now());
        Flux<Contact> mockResponse = Flux.fromIterable(Arrays.asList(contact1, contact2));

        Mockito.when(contactService.getAllContacts()).thenReturn(mockResponse);

        webTestClient.get()
                .uri("/v3/contacts")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBodyList(Contact.class)
                .hasSize(2);

        Mockito.verify(contactService, Mockito.times(1)).getAllContacts();
    }
}