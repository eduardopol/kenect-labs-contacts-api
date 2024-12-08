package com.kenect.service;

import com.kenect.dto.ContactListDto;
import com.kenect.http.ContactsHttpClient;
import com.kenect.service.impl.AsyncContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AsyncContactServiceTest {

    @Mock
    private ContactsHttpClient contactsHttpClient;

    @InjectMocks
    private AsyncContactServiceImpl asyncContactService;

    AsyncContactServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchContactsPageAsync() throws Exception {
        ContactListDto mockResponse = ContactListDto.builder()
                .currentPage(1)
                .totalPages(3)
                .build();

        when(contactsHttpClient.fetchContactsPage(1)).thenReturn(mockResponse);

        CompletableFuture<ContactListDto> future = asyncContactService.fetchContactsPageAsync(1);

        assertThat(future).isNotNull();
        assertThat(future.get()).isEqualTo(mockResponse);

        verify(contactsHttpClient, times(1)).fetchContactsPage(1);
    }
}