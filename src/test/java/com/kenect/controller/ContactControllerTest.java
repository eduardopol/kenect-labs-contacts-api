package com.kenect.controller;

import com.kenect.http.ContactsHttpClient;
import com.kenect.model.Contact;
import com.kenect.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactControllerTest {

    private ContactController contactController;

    @BeforeEach
    void setUp() {
        ContactServiceImpl contactService = new ContactServiceImpl(new ContactsHttpClient());
        contactController = new ContactController(contactService);
    }

    @Test
    public void testGetAllContactsWithEmptyResponse() {
        List<Contact> contacts = contactController.getAllContacts();

        assertNotNull(contacts);
        assertTrue(contacts.isEmpty(), "Expected an empty list when no contacts are available");

    }

}
