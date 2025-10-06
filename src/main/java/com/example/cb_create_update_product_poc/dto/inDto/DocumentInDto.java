package com.example.cb_create_update_product_poc.dto.inDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DocumentInDto {

    @NotBlank(message = "Document type cannot be empty.")
    @Size(max = 100, message = "Document type cannot exceed 100 characters.")
    private String documentType;

    @Size(max = 2000, message = "Comment cannot exceed 2000 characters.")
    private String comment;
}

