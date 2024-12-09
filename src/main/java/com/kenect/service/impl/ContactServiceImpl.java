package com.kenect.service.impl;

import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import com.kenect.http.ContactsHttpClient;
import com.kenect.mapper.ContactMapper;
import com.kenect.model.Contact;
import com.kenect.service.AsyncContactService;
import com.kenect.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the {@link ContactService} interface.
 *
 * Fetches contact information from the database.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactsHttpClient contactsHttpClient;
    private final AsyncContactService asyncContactService;

    @Override
    @Cacheable("contacts")
    public List<Contact> getAllContacts() {
        log.debug("Getting all contacts");
        List<ContactDto> contacts = new ArrayList<>();
        int page = 1;
        boolean hasMorePages = true;

        while (hasMorePages) {
            ContactListDto contactListDto = contactsHttpClient.fetchContactsPage(page);
            contacts.addAll(contactListDto.getContacts());

            hasMorePages = contactListDto.getTotalPages() > contactListDto.getCurrentPage();
            page++;
        }
        log.debug("Finished getting {} contacts from {} pages", contacts.size(), page - 1);
        return ContactMapper.toContactList(contacts);
    }

    @Override
    @Cacheable("contactsAsync")
    public List<Contact> getAllContactsAsync() {
        log.debug("Fetching all contacts asynchronously...");
        List<CompletableFuture<ContactListDto>> futures = new ArrayList<>();
        int page = 1;
        int totalPages = Integer.MAX_VALUE;

        while (page <= totalPages) {
            CompletableFuture<ContactListDto> future = asyncContactService.fetchContactsPageAsync(page);
            futures.add(future);

            if (page == 1) {
                try {
                    ContactListDto firstPage = future.get();
                    totalPages = firstPage.getTotalPages();
                } catch (Exception e) {
                    log.error("Error fetching contacts asynchronously", e);
                    throw new RuntimeException("Error during async processing", e);
                }
            }

            page++;
        }

        List<ContactDto> contacts = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(contactList -> contactList.getContacts().stream())
                .toList();

        log.debug("Successfully fetched {} contacts", contacts.size());
        return ContactMapper.toContactList(contacts);
    }

}
