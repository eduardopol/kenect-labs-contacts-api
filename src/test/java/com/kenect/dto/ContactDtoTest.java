package com.kenect.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenect.factory.ObjectMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContactDtoTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = ObjectMapperFactory.createObjectMapper();
    }

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

    @Test
    void testDeserializeContactDto() throws Exception {
        String json = """
                {
                    "id": 1,
                    "name": "John Doe",
                    "email": "johndoe@example.net",
                    "createdAt": "2020-06-24T19:37:16.688Z",
                    "updatedAt": "2022-05-05T15:27:17.547Z"
                }
                """;

        ContactDto contactDto = objectMapper.readValue(json, ContactDto.class);

        assertNotNull(contactDto);
        assertEquals(1L, contactDto.getId());
        assertEquals("John Doe", contactDto.getName());
        assertEquals(LocalDateTime.of(2020, 6, 24, 19, 37, 16, 688_000_000), contactDto.getCreatedAt());
    }
}
