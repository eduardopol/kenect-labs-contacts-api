package com.kenect.controller;

import com.kenect.model.Contact;
import com.kenect.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/contacts")
    public List<Contact> contacts() {
        return contactService.getAllContacts();
    }

}
