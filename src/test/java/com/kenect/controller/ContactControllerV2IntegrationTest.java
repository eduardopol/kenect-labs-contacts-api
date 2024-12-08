package com.kenect.controller;

import com.kenect.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactControllerV2IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllContactsV2() {
        ResponseEntity<Contact[]> response = restTemplate.getForEntity("/v2/contacts", Contact[].class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        Contact[] contacts = response.getBody();
        assertNotNull(contacts);
        assertTrue(contacts.length > 0);
    }
}
