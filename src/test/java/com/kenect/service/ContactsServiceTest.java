package com.kenect.service;

import com.kenect.http.ContactsHttpClient;
import com.kenect.model.Contact;
import com.kenect.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactsServiceTest {

    private ContactServiceImpl contactService;

    @BeforeEach
    void setUp() {
        contactService = new ContactServiceImpl(new ContactsHttpClient());

    }

    @Test
    public void testGetAllContactsWithEmptyResponse() {
        List<Contact> contacts = contactService.getAllContacts();

        assertNotNull(contacts);
        assertTrue(contacts.isEmpty(), "Expected an empty list when no contacts are available");
    }

    @Test
    public void testGetAllContactsWithSingleItemResponse() {
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
