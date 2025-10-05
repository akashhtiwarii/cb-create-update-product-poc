package com.example.cb_create_update_product_poc.dto.inDto;

import com.example.cb_create_update_product_poc.validation.CreateProductValidationGroups;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDraftInDto {

    //Product
    @NotBlank(message = "Product name cannot be empty.", groups = {CreateProductValidationGroups.OnSaveDraft.class, CreateProductValidationGroups.OnSubmit.class})
    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters.", groups = {CreateProductValidationGroups.OnSaveDraft.class, CreateProductValidationGroups.OnSubmit.class})
    private String name;

    @NotBlank(message = "Product type cannot be empty.", groups = {CreateProductValidationGroups.OnSaveDraft.class, CreateProductValidationGroups.OnSubmit.class})
    @Size(max = 50, message = "Product type cannot exceed 50 characters.", groups = {CreateProductValidationGroups.OnSaveDraft.class, CreateProductValidationGroups.OnSubmit.class})
    private String productType;

    //ProductVersion
    @NotBlank(message = "Configuration cannot be empty.", groups = CreateProductValidationGroups.OnSubmit.class)
    private Map<String, Object> configuration;

    @FutureOrPresent(message = "Effective start date must be in the present or future.", groups = CreateProductValidationGroups.OnSubmit.class)
    private LocalDateTime effectiveStartDate;

    @Size(max = 2000, message = "Comment cannot exceed 2000 characters.", groups = {CreateProductValidationGroups.OnSaveDraft.class, CreateProductValidationGroups.OnSubmit.class})
    private String comment;
}
