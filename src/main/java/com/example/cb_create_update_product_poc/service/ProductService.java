package com.example.cb_create_update_product_poc.service;

import com.example.cb_create_update_product_poc.dto.inDto.ProductDraftInDto;
import com.example.cb_create_update_product_poc.dto.outDto.ProductDraftOutDto;

import java.util.UUID;

public interface ProductService {
    ProductDraftOutDto createProductDraft(ProductDraftInDto productDraftInDto, UUID userId);
}
