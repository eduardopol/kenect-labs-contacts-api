package com.kenect.service;

import com.kenect.model.Contact;
import com.kenect.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContactsServiceTest {

    private ContactServiceImpl contactService;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        contactService = new ContactServiceImpl();
    }

    @Test
    public void testGetAllContactsWithEmptyResponse() {
        when(restTemplate.getForObject(anyString(), eq(Contact[].class)))
                .thenReturn(new Contact[0]);

        List<Contact> contacts = contactService.getAllContacts();

        assertNotNull(contacts);
        assertTrue(contacts.isEmpty(), "Expected an empty list when no contacts are available");
    }

    @Test
    public void testGetAllContactsWithSingleItemResponse() {
        when(restTemplate.getForObject(anyString(), eq(Contact[].class)))
                .thenReturn(new Contact[0]);

        List<Contact> contacts = contactService.getAllContacts();

        assertNotNull(contacts);
        assertTrue(!contacts.isEmpty(), "Expected a list when with a single contact");
    }

   /* @Test
    public void testGetAllContactsWithSingleItemResponse() {

        List<Contact> contacts = contactService.getAllContacts();

        assertNotNull(contacts);
        assertTrue(!contacts.isEmpty(), "Expected a list when with a single contact");
    }

    public Contact generateRandomContact() {
        long id = ThreadLocalRandom.current().nextLong(1, 10000);

        String name = "Contact " + id;

        String email = "contact" + id + "@example.com";

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime randomCreatedAt = now.minusDays(ThreadLocalRandom.current().nextInt(1, 365));
        LocalDateTime randomUpdatedAt = randomCreatedAt.plusDays(ThreadLocalRandom.current().nextInt(1, 365 - (int) randomCreatedAt.until(now).toDays()));

        return Contact.builder()
                .id(id)
                .name(name)
                .email(email)
                .createdAt(randomCreatedAt)
                .updatedAt(randomUpdatedAt)
                .build();
    }
*/
}
