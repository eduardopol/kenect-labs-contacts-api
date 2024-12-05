package com.kenect.mapper;

import com.kenect.dto.ContactDto;
import com.kenect.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContactMapper {

    public static List<Contact> toContactList(List<ContactDto> contactList) {
        if (Objects.isNull(contactList) || contactList.isEmpty()) {
            return new ArrayList<>();
        }

        return contactList.stream().map(ContactMapper::toContact).collect(Collectors.toList());
    }

    public static Contact toContact(ContactDto contact) {
        if (Objects.isNull(contact)) {
            return null;
        }

        return Contact.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .build();
    }
}
