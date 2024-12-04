package com.kenect.service.impl;

import com.kenect.model.Contact;
import com.kenect.service.ContactService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Override
    public List<Contact> getAllContacts() {
        return List.of();
    }

}
