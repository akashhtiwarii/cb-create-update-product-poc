package com.example.cb_create_update_product_poc.service;

import com.example.cb_create_update_product_poc.dto.inDto.DocumentInDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface DocumentService {
    void addDocumentToProductVersion(UUID productVersionId, DocumentInDto documentInDto, MultipartFile file, UUID userId);
}
