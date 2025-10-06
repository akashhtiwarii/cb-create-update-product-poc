package com.example.cb_create_update_product_poc.controller;

import com.example.cb_create_update_product_poc.dto.inDto.ProductDraftInDto;
import com.example.cb_create_update_product_poc.dto.outDto.ApiResponseOutDto;
import com.example.cb_create_update_product_poc.dto.outDto.ProductDraftOutDto;
import com.example.cb_create_update_product_poc.service.ProductService;
import com.example.cb_create_update_product_poc.validation.CreateProductValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseOutDto<ProductDraftOutDto>> createProductDraft(
            @Validated(CreateProductValidationGroups.OnSaveDraft.class) @RequestBody ProductDraftInDto productDraftInDto,
            @RequestHeader("X-User-ID") UUID userId) {

        ProductDraftOutDto createdDraft = productService.createProductDraft(productDraftInDto, userId);

        return new ResponseEntity<>(ApiResponseOutDto.success(createdDraft, "Product draft created successfully"), HttpStatus.CREATED);
    }
}
