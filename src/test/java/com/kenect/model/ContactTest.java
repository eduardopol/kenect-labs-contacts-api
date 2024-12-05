package com.kenect.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactTest {

    @Test
    public void testContactBuilderAndDefaultSource() {
        Long expectedId = 1L;
        String expectedName = "John Doe";
        String expectedEmail = "john.doe@email.com";
        LocalDateTime expectedCreatedAt = LocalDateTime.now();
        LocalDateTime expectedUpdatedAt = LocalDateTime.now();

        Contact contact = Contact.builder()
                .id(expectedId)
                .name(expectedName)
                .email(expectedEmail)
                .createdAt(expectedCreatedAt)
                .updatedAt(expectedUpdatedAt)
                .build();

        assertNotNull(contact);
        assertEquals(expectedId, contact.getId());
        assertEquals(expectedName, contact.getName());
        assertEquals(expectedEmail, contact.getEmail());
        assertEquals("KENECT_LABS", contact.getSource());
        assertEquals(expectedCreatedAt, contact.getCreatedAt());
        assertEquals(expectedUpdatedAt, contact.getUpdatedAt());
    }
}
