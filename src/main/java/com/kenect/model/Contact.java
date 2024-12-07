package com.kenect.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents the contact data")
public class Contact {

    @Schema(description = "Unique identifier of the contact", example = "1")
    private Long id;
    @Schema(description = "Full name of the contact", example = "John Doe")
    private String name;
    @Schema(description = "Email address of the contact", example = "john.doe@example.com")
    private String email;
    @Schema(description = "Source system of the contact", example = "KENECT_LABS", defaultValue = "KENECT_LABS", readOnly = true)
    private final String source = "KENECT_LABS";

    @Schema(
            description = "Timestamp when the contact was created",
            example = "2024-12-07T00:44:44.741Z"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp when the contact was last updated",
            example = "2024-12-07T00:44:44.741Z"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime updatedAt;

}
