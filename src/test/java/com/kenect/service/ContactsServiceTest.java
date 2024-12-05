package com.kenect.service;

import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import com.kenect.http.ContactsHttpClient;
import com.kenect.model.Contact;
import com.kenect.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContactServiceImplTest {

    @Mock
    private ContactsHttpClient contactsHttpClient;

    @InjectMocks
    private ContactServiceImpl contactService;

    public ContactServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllContacts_MultiplePages() {
        // Arrange
        List<ContactDto> page1 = List.of(
                ContactDto.builder().id(1L).name("John Doe").email("john.doe@example.com").build(),
                ContactDto.builder().id(2L).name("Jane Doe").email("jane.doe@example.com").build()
        );
        List<ContactDto> page2 = List.of(
                ContactDto.builder().id(3L).name("Jack Doe").email("jack.doe@example.com").build()
        );

        when(contactsHttpClient.fetchContactsPage(1)).thenReturn(ContactListDto.builder()
                .contacts(page1).currentPage(1).totalPages(2).build());
        when(contactsHttpClient.fetchContactsPage(2)).thenReturn(ContactListDto.builder()
                .contacts(page2).currentPage(2).totalPages(2).build());

        // Act
        List<Contact> result = contactService.getAllContacts();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
        assertEquals("Jack Doe", result.get(2).getName());
        verify(contactsHttpClient, times(2)).fetchContactsPage(anyInt());
    }

    @Test
    void testGetAllContacts_SinglePage() {
        // Arrange
        List<ContactDto> singlePage = List.of(
                ContactDto.builder().id(1L).name("John Doe").email("john.doe@example.com").build()
        );

        when(contactsHttpClient.fetchContactsPage(1)).thenReturn(ContactListDto.builder()
                .contacts(singlePage).currentPage(1).totalPages(1).build());

        // Act
        List<Contact> result = contactService.getAllContacts();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(contactsHttpClient, times(1)).fetchContactsPage(anyInt());
    }

    @Test
    void testGetAllContacts_EmptyResponse() {
        // Arrange
        when(contactsHttpClient.fetchContactsPage(1)).thenReturn(ContactListDto.builder()
                .contacts(List.of()).currentPage(1).totalPages(1).build());

        // Act
        List<Contact> result = contactService.getAllContacts();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(contactsHttpClient, times(1)).fetchContactsPage(anyInt());
    }

    @Test
    void testGetAllContacts_ApiException() {
        // Arrange
        when(contactsHttpClient.fetchContactsPage(1)).thenThrow(new RuntimeException("API Error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> contactService.getAllContacts());
        assertEquals("API Error", exception.getMessage());
        verify(contactsHttpClient, times(1)).fetchContactsPage(anyInt());
    }
}
