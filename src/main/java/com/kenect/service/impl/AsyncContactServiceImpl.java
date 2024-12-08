package com.kenect.service.impl;

import com.kenect.dto.ContactListDto;
import com.kenect.http.ContactsHttpClient;
import com.kenect.service.AsyncContactService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncContactServiceImpl implements AsyncContactService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncContactServiceImpl.class);

    private final ContactsHttpClient contactsHttpClient;

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<ContactListDto> fetchContactsPageAsync(Integer page) {
        logger.info("Fetching contacts for page {} on thread: {}", page, Thread.currentThread().getName());
        ContactListDto contactListDto = contactsHttpClient.fetchContactsPage(page);
        return CompletableFuture.completedFuture(contactListDto);
    }

}