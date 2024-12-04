package mapper;

import com.kenect.dto.ContactDto;
import com.kenect.mapper.ContactMapper;
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
    void testNullContactDtoToContactMapping() {
        Contact contact = ContactMapper.toContact(null);

        assertNull(contact);
    }

    @Test
    void testContactDtoToContactMapping() {
        LocalDateTime now = LocalDateTime.now();

        ContactDto dto = ContactDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@email.com")
                .createdAt(now)
                .updatedAt(now.plusDays(1))
                .build();

        Contact contact = ContactMapper.toContact(dto);

        assertNotNull(contact);
        assertEquals(dto.getId(), contact.getId());
        assertEquals(dto.getName(), contact.getName());
        assertEquals(dto.getEmail(), contact.getEmail());
        assertEquals(dto.getSource(), contact.getSource());
        assertEquals(now, contact.getCreatedAt());
        assertEquals(now.plusDays(1), contact.getUpdatedAt());
    }

    @Test
    void testContactDtoListToContactListMapping() {
        LocalDateTime now = LocalDateTime.now();

        ContactDto dto = ContactDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@email.com")
                .createdAt(now)
                .updatedAt(now.plusDays(1))
                .build();

        ContactDto dto2 = ContactDto.builder()
                .id(1L)
                .name("Jane Doe")
                .email("jane.doe@email.com")
                .createdAt(now)
                .updatedAt(now.plusDays(1))
                .build();

        List<Contact> contacts = ContactMapper.toContactList(List.of(dto, dto2));

        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());
        assertEquals(2, contacts.size());
        assertEquals(dto.getId(), contacts.getFirst().getId());
        assertEquals(dto.getName(), contacts.getFirst().getName());
        assertEquals(dto.getEmail(), contacts.getFirst().getEmail());
        assertEquals(dto.getSource(), contacts.getFirst().getSource());
        assertEquals(now, contacts.getFirst().getCreatedAt());
        assertEquals(now.plusDays(1), contacts.getFirst().getUpdatedAt());
    }
}
