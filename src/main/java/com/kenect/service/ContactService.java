package com.kenect.service;

import com.kenect.model.Contact;

import java.util.List;

/**
 * Service interface for managing contacts.
 *
 * Defines operations for fetching and managing contact information.
 */
public interface ContactService {

    /**
     * Retrieve all contacts.
     *
     * @return a list of {@link Contact} objects representing all available contacts.
     */
    List<Contact> getAllContacts();

    /**
     * Fetches all contacts asynchronously, combining results from paginated API calls.
     * The results are cached to improve performance for subsequent requests.
     *
     * @return a list of {@link Contact} objects representing all contacts.
     */
    List<Contact> getAllContactsAsync();

}
