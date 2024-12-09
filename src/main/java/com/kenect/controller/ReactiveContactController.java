package com.kenect.controller;

import com.kenect.dto.DefaultErrorResponse;
import com.kenect.model.Contact;
import com.kenect.service.impl.ReactiveContactServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Fully reactive controller for managing contacts.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Reactive Contacts", description = "Reactive Contacts API")
public class ReactiveContactController {

    private final ReactiveContactServiceImpl contactService;

    /**
     * Endpoint to fetch all contacts reactively.
     *
     * @return a Flux stream of Contact objects.
     */
    @Operation(summary = "Fetch all contacts reactively", description = "Retrieves a list of all contacts reactively.")
    @ApiResponse(responseCode = "200", description = "Successfully fetched contacts",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DefaultErrorResponse.class)))
    @GetMapping(value = "/v3/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Contact> getAllContacts() {
        log.info("Received a call to get all contacts reactively");
        return contactService.getAllContacts().limitRate(100);
    }
}