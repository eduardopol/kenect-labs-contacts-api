package com.kenect.mapper;

import com.kenect.dto.ContactDto;
import com.kenect.model.Contact;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ContactMapperTest {

    @Test
    void testNullContactToContactDtoMapping() {
        Contact contact = ContactMapper.toContact(null);

        assertNull(contact);
    }

    @Test
    void testContactDtoToContactDtoMapping() {
        LocalDateTime now = LocalDateTime.now();

        ContactDto contact = ContactDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@email.com")
                .createdAt(now)
                .updatedAt(now.plusDays(1))
                .build();

        Contact contactDto = ContactMapper.toContact(contact);

        assertNotNull(contactDto);
        assertEquals(contact.getId(), contactDto.getId());
        assertEquals(contact.getName(), contactDto.getName());
        assertEquals(contact.getEmail(), contactDto.getEmail());
        assertEquals("KENECT_LABS", contactDto.getSource());
        assertEquals(now, contactDto.getCreatedAt());
        assertEquals(now.plusDays(1), contactDto.getUpdatedAt());
    }

    @Test
    void testContactDtoListToContactDtoListMapping() {
        LocalDateTime now = LocalDateTime.now();

        ContactDto contact = ContactDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@email.com")
                .createdAt(now)
                .updatedAt(now.plusDays(1))
                .build();

        ContactDto contact2 = ContactDto.builder()
                .id(1L)
                .name("Jane Doe")
                .email("jane.doe@email.com")
                .createdAt(now)
                .updatedAt(now.plusDays(1))
                .build();

        List<Contact> contacts = ContactMapper.toContactList(List.of(contact, contact2));

        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());
        assertEquals(2, contacts.size());
        assertEquals(contact.getId(), contacts.getFirst().getId());
        assertEquals(contact.getName(), contacts.getFirst().getName());
        assertEquals(contact.getEmail(), contacts.getFirst().getEmail());
        assertEquals("KENECT_LABS", contacts.getFirst().getSource());
        assertEquals(now, contacts.getFirst().getCreatedAt());
        assertEquals(now.plusDays(1), contacts.getFirst().getUpdatedAt());
    }
}
