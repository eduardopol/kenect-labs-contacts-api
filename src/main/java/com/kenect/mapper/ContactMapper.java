package com.kenect.mapper;

import com.kenect.dto.ContactDto;
import com.kenect.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between {@link ContactDto} and {@link Contact}.
 *
 * This class provides utility methods for mapping a single {@link ContactDto}
 * or a list of {@link ContactDto} objects to their corresponding {@link Contact} entities.
 */
public class ContactMapper {

    /**
     * Converts a list of {@link ContactDto} objects to a list of {@link Contact} entities.
     *
     * If the input list is null or empty, an empty list is returned.
     *
     * @param contactList the list of {@link ContactDto} objects to convert.
     * @return a list of {@link Contact} entities.
     */
    public static List<Contact> toContactList(List<ContactDto> contactList) {
        if (Objects.isNull(contactList) || contactList.isEmpty()) {
            return new ArrayList<>();
        }

        return contactList.stream().map(ContactMapper::toContact).collect(Collectors.toList());
    }

    /**
     * Converts a single {@link ContactDto} object to a {@link Contact} entity.
     *
     * <p>If the input {@link ContactDto} is null, the method returns null.</p>
     *
     * @param contact the {@link ContactDto} object to convert.
     * @return a {@link Contact} entity or null if the input is null.
     */
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
