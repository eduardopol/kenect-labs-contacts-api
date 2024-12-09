package com.kenect.service;

import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import com.kenect.http.ReactiveContactsHttpClient;
import com.kenect.model.Contact;
import com.kenect.service.impl.ReactiveContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ReactiveContactServiceTest {

    private ReactiveContactServiceImpl service;
    private ReactiveContactsHttpClient contactsHttpClient;

    @BeforeEach
    void setUp() {
        contactsHttpClient = Mockito.mock(ReactiveContactsHttpClient.class);
        service = new ReactiveContactServiceImpl(contactsHttpClient);
    }

    @Test
    void getAllContacts_ShouldReturnSortedContacts() {
        List<ContactDto> firstPageDtos = Arrays.asList(
                new ContactDto(2L, "Jane Smith", "jane@example.com", LocalDateTime.now(), LocalDateTime.now()),
                new ContactDto(4L, "Alice Doe", "alice@example.com", LocalDateTime.now(), LocalDateTime.now())
        );
        ContactListDto firstPage = new ContactListDto(firstPageDtos, 1, 6, 3);

        List<ContactDto> secondPageDtos = Collections.singletonList(
                new ContactDto(1L, "John Doe", "john@example.com", LocalDateTime.now(), LocalDateTime.now())
        );
        ContactListDto secondPage = new ContactListDto(secondPageDtos, 2, 6, 3);

        List<ContactDto> thirdPageDtos = Collections.singletonList(
                new ContactDto(3L, "Bob Smith", "bob@example.com", LocalDateTime.now(), LocalDateTime.now())
        );
        ContactListDto thirdPage = new ContactListDto(thirdPageDtos, 3, 6, 3);

        Mockito.when(contactsHttpClient.fetchContactsPage(1)).thenReturn(Mono.just(firstPage));
        Mockito.when(contactsHttpClient.fetchContactsPage(2)).thenReturn(Mono.just(secondPage));
        Mockito.when(contactsHttpClient.fetchContactsPage(3)).thenReturn(Mono.just(thirdPage));

        Flux<Contact> result = service.getAllContacts();

        StepVerifier.create(result)
                .expectNextMatches(contact -> contact.getId() == 1L)
                .expectNextMatches(contact -> contact.getId() == 2L)
                .expectNextMatches(contact -> contact.getId() == 3L)
                .expectNextMatches(contact -> contact.getId() == 4L)
                .verifyComplete();

        Mockito.verify(contactsHttpClient, Mockito.times(1)).fetchContactsPage(1);
        Mockito.verify(contactsHttpClient, Mockito.times(1)).fetchContactsPage(2);
        Mockito.verify(contactsHttpClient, Mockito.times(1)).fetchContactsPage(3);
    }

    @Test
    void getAllContacts_ShouldHandleEmptyContacts() {
        ContactListDto emptyPage = new ContactListDto(Collections.emptyList(), 1, 0, 1);

        Mockito.when(contactsHttpClient.fetchContactsPage(1)).thenReturn(Mono.just(emptyPage));

        Flux<Contact> result = service.getAllContacts();

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        Mockito.verify(contactsHttpClient, Mockito.times(1)).fetchContactsPage(1);
    }

    @Test
    void getAllContacts_ShouldHandleErrorsGracefully() {
        Mockito.when(contactsHttpClient.fetchContactsPage(1))
                .thenReturn(Mono.error(new RuntimeException("Failed to fetch contacts")));

        Flux<Contact> result = service.getAllContacts();

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Failed to fetch contacts"))
                .verify();

        Mockito.verify(contactsHttpClient, Mockito.times(1)).fetchContactsPage(1);
    }
}