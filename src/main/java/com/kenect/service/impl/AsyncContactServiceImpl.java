package com.kenect.service.impl;

import com.kenect.dto.ContactListDto;
import com.kenect.http.ContactsHttpClient;
import com.kenect.service.AsyncContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncContactServiceImpl implements AsyncContactService {

    private final ContactsHttpClient contactsHttpClient;

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<ContactListDto> fetchContactsPageAsync(Integer page) {
        log.debug("Fetching contacts for page {} on thread: {}", page, Thread.currentThread().getName());
        ContactListDto contactListDto = contactsHttpClient.fetchContactsPage(page);
        return CompletableFuture.completedFuture(contactListDto);
    }

}