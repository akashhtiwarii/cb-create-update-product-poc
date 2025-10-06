package com.example.cb_create_update_product_poc.controller;

import com.example.cb_create_update_product_poc.dto.inDto.DocumentInDto;
import com.example.cb_create_update_product_poc.dto.outDto.ApiResponseOutDto;
import com.example.cb_create_update_product_poc.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/product-versions/{productVersionId}/documents")
    public ResponseEntity<ApiResponseOutDto<Void>> uploadDocument(
            @PathVariable UUID productVersionId,
            @RequestPart("file") MultipartFile file,
            @Valid DocumentInDto documentInDto,
            @RequestHeader("X-User-ID") UUID userId) {

        documentService.addDocumentToProductVersion(productVersionId, documentInDto, file, userId);

        return new ResponseEntity<>(ApiResponseOutDto.success(null, "Document uploaded successfully"), HttpStatus.CREATED);
    }
}
