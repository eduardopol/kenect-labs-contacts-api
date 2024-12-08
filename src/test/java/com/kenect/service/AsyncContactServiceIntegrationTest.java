package com.kenect.service;

import com.kenect.dto.ContactListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AsyncContactServiceIntegrationTest {

    @Autowired
    private AsyncContactService asyncContactService;

    @Test
    void testFetchContactsPageAsyncIntegration() throws Exception {
        CompletableFuture<ContactListDto> future = asyncContactService.fetchContactsPageAsync(1);

        assertThat(future).isNotNull();

        ContactListDto result = future.get();
        assertThat(result).isNotNull();
        assertThat(result.getCurrentPage()).isEqualTo(1);

        String threadName = Thread.currentThread().getName();
        assertThat(threadName).doesNotStartWith("AsyncThread-");
    }
}