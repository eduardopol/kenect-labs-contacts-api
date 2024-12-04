package com.kenect.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContactDtoTest {

    @Test
    public void testContactBuilderAndDefaultSource() {
        Long expectedId = 1L;
        String expectedName = "John Doe";
        String expectedEmail = "john.doe@email.com";
        LocalDateTime expectedCreatedAt = LocalDateTime.now();
        LocalDateTime expectedUpdatedAt = LocalDateTime.now();

        ContactDto contact = ContactDto.builder()
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
        assertEquals(expectedCreatedAt, contact.getCreatedAt());
        assertEquals(expectedUpdatedAt, contact.getUpdatedAt());
    }

}
