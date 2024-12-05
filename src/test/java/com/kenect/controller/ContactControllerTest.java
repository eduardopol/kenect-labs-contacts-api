package com.kenect.controller;

import com.kenect.model.Contact;
import com.kenect.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContactService contactService;

    @Test
    void testGetAllContacts() throws Exception {
        List<Contact> mockContacts = List.of(
                Contact.builder().id(1L).name("John Doe").email("john.doe@example.com").build(),
                Contact.builder().id(2L).name("Jane Doe").email("jane.doe@example.com").build()
        );
        when(contactService.getAllContacts()).thenReturn(mockContacts);

        mockMvc.perform(get("/v1/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }
}
