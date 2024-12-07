package com.kenect.service;

import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import com.kenect.http.ContactsHttpClient;
import com.kenect.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContactsServiceIntegrationTest {

    @Autowired
    private ContactServiceImpl contactService;

    @MockitoBean
    private ContactsHttpClient contactsHttpClient;

    @Test
    void testCachingReducesServiceCalls() {
        List<ContactDto> mockContacts = List.of(
                ContactDto.builder().id(1L).name("John Doe").email("john.doe@example.com").build(),
                ContactDto.builder().id(2L).name("Jane Doe").email("jane.doe@example.com").build()
        );
        when(contactsHttpClient.fetchContactsPage(any())).thenReturn(ContactListDto.builder()
                        .contacts(mockContacts).totalPages(1).currentPage(1).build());

        contactService.getAllContacts();
        contactService.getAllContacts();

        verify(contactsHttpClient, times(1)).fetchContactsPage(any());
    }
}