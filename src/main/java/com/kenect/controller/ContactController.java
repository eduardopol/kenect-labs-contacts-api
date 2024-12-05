package com.kenect.controller;

import com.kenect.model.Contact;
import com.kenect.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;

    @GetMapping("/contacts")
    public List<Contact> getAllContacts() {
        logger.info("Received a call to get all contacts");
        return contactService.getAllContacts();
    }

}
