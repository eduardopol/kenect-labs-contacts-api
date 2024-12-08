package com.kenect.service;

import com.kenect.dto.ContactListDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface AsyncContactService {

    /**
     * Fetches a specific page of contacts asynchronously from the API.
     *
     * @param page the page number to fetch.
     * @return a {@link CompletableFuture} containing a {@link ContactListDto} object for the given page.
     */
    @Async("asyncExecutor")
    CompletableFuture<ContactListDto> fetchContactsPageAsync(Integer page);

}
