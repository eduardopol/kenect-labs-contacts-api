package com.kenect.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContactListDtoTest {

    @Test
    public void testContactListBuilderAndDefaultSource() {
        Long expectedId = 1L;
        String expectedName = "John Doe";
        String expectedEmail = "john.doe@email.com";
        LocalDateTime expectedCreatedAt = LocalDateTime.now();
        LocalDateTime expectedUpdatedAt = LocalDateTime.now();
        Integer totalContacts = 40;
        Integer totalPages = 2;
        Integer currentPage = 1;

        ContactDto contact = ContactDto.builder()
                .id(expectedId)
                .name(expectedName)
                .email(expectedEmail)
                .createdAt(expectedCreatedAt)
                .updatedAt(expectedUpdatedAt)
                .build();

        ContactListDto contactListDto = ContactListDto.builder()
                .contacts(List.of(contact))
                .totalContacts(totalContacts)
                .totalPages(totalPages)
                .currentPage(currentPage)
                .build();

        assertNotNull(contactListDto);
        assertEquals(totalContacts, contactListDto.getTotalContacts());
        assertEquals(totalPages, contactListDto.getTotalPages());
        assertEquals(currentPage, contactListDto.getCurrentPage());
        assertEquals(expectedId, contactListDto.getContacts().getFirst().getId());
        assertEquals(expectedName, contactListDto.getContacts().getFirst().getName());
        assertEquals(expectedEmail, contactListDto.getContacts().getFirst().getEmail());
        assertEquals(expectedCreatedAt, contactListDto.getContacts().getFirst().getCreatedAt());
        assertEquals(expectedUpdatedAt, contactListDto.getContacts().getFirst().getUpdatedAt());
    }

}
