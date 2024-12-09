package com.kenect.service;

import com.kenect.model.Contact;
import reactor.core.publisher.Flux;

/**
 * Fully reactive service for managing contacts.
 */
public interface ReactiveContactService {

    /**
     * Fetches all contacts as a reactive Flux.
     *
     * @return a Flux stream of Contact objects.
     */
    public Flux<Contact> getAllContacts();
}