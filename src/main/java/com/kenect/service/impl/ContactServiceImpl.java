package com.kenect.service.impl;

import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import com.kenect.http.ContactsHttpClient;
import com.kenect.mapper.ContactMapper;
import com.kenect.model.Contact;
import com.kenect.service.AsyncContactService;
import com.kenect.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactsHttpClient contactsHttpClient;
    private final AsyncContactService asyncContactService;

    @Override
    @Cacheable("contacts")
    public List<Contact> getAllContacts() {
        logger.info("Getting all contacts");
        List<ContactDto> contacts = new ArrayList<>();
        int page = 1;
        boolean hasMorePages = true;

        while (hasMorePages) {
            ContactListDto contactListDto = contactsHttpClient.fetchContactsPage(page);
            contacts.addAll(contactListDto.getContacts());

            hasMorePages = contactListDto.getTotalPages() > contactListDto.getCurrentPage();
            page++;
        }
        logger.info("Finished getting {} contacts from {} pages", contacts.size(), page - 1);
        return ContactMapper.toContactList(contacts);
    }

    @Override
    @Cacheable("contactsAsync")
    public List<Contact> getAllContactsAsync() {
        logger.info("Fetching all contacts asynchronously...");
        List<CompletableFuture<ContactListDto>> futures = new ArrayList<>();
        int page = 1;
        int totalPages = Integer.MAX_VALUE; // Placeholder for total pages

        while (page <= totalPages) {
            CompletableFuture<ContactListDto> future = asyncContactService.fetchContactsPageAsync(page);
            futures.add(future);

            if (page == 1) {
                try {
                    ContactListDto firstPage = future.get();
                    totalPages = firstPage.getTotalPages();
                } catch (Exception e) {
                    logger.error("Error fetching contacts asynchronously", e);
                    throw new RuntimeException("Error during async processing", e);
                }
            }

            page++;
        }

        List<ContactDto> contacts = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(contactList -> contactList.getContacts().stream())
                .toList();

        logger.info("Successfully fetched {} contacts", contacts.size());
        return ContactMapper.toContactList(contacts);
    }

}
