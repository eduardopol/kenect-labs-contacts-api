package com.kenect.service.impl;

import com.kenect.dto.ContactDto;
import com.kenect.dto.ContactListDto;
import com.kenect.http.ContactsHttpClient;
import com.kenect.mapper.ContactMapper;
import com.kenect.model.Contact;
import com.kenect.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactsHttpClient contactsHttpClient;

    @Override
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

}
