package com.kenect.service.impl;

import com.kenect.http.ReactiveContactsHttpClient;
import com.kenect.mapper.ContactMapper;
import com.kenect.model.Contact;
import com.kenect.service.ReactiveContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveContactServiceImpl implements ReactiveContactService {

    private final ReactiveContactsHttpClient contactsHttpClient;

    @Override
    public Flux<Contact> getAllContacts() {
        return contactsHttpClient.fetchContactsPage(1)
                .flatMapMany(firstPage -> {
                    int totalPages = firstPage.getTotalPages();
                    return Flux.concat(
                            Flux.just(firstPage),
                            Flux.fromStream(IntStream.range(2, totalPages + 1).mapToObj(contactsHttpClient::fetchContactsPage))
                                    .flatMap(mono -> mono)
                    );
                })
                .flatMap(contactList -> Flux.fromIterable(contactList.getContacts()))
                .map(ContactMapper::toContact)
                .sort(Comparator.comparingLong(Contact::getId));
    }
}