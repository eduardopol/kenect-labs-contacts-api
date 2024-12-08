package com.kenect.controller;

import com.kenect.dto.DefaultErrorResponse;
import com.kenect.model.Contact;
import com.kenect.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for getting contacts version 2.
 * This controller provides API to retrieve contact information.
 */
@RestController
@RequestMapping("/v2/contacts")
@RequiredArgsConstructor
@Tag(name = "Contacts", description = "Contacts API")
public class ContactControllerV2 {

    private static final Logger logger = LoggerFactory.getLogger(ContactControllerV2.class);

    private final ContactService contactService;

    /**
     * Retrieve a list of all contacts.
     * This API endpoint fetches all contacts from the service layer.
     *
     * @return a list of {@link Contact} objects representing all available contacts.
     */
    @Operation(summary = "Fetch all contacts", description = "Retrieves a list of all contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched contacts"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DefaultErrorResponse.class)))
    })
    @GetMapping
    public List<Contact> getAllContacts() {
        logger.info("Received a call to get all contacts");
        return contactService.getAllContactsAsync();
    }

}
