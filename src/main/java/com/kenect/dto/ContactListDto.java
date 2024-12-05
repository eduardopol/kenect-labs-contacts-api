package com.kenect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactListDto {

    private List<ContactDto> contacts;
    private Integer currentPage;
    private Integer totalContacts;
    private Integer totalPages;

}
