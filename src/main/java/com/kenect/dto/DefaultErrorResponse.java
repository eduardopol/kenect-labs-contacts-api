package com.kenect.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Default error response from Spring Boot")
public class DefaultErrorResponse {

    @Schema(description = "Timestamp of the error", example = "1733591286612")
    private long timestamp;

    @Schema(description = "HTTP status code of the error", example = "500")
    private int status;

    @Schema(description = "Error type", example = "Internal Server Error")
    private String error;

    @Schema(description = "Error path", example = "/kenect-labs/v1/contacts")
    private String path;
}