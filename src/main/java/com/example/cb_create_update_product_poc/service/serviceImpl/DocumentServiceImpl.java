package com.example.cb_create_update_product_poc.service.serviceImpl;

import com.example.cb_create_update_product_poc.dto.inDto.DocumentInDto;
import com.example.cb_create_update_product_poc.entity.Document;
import com.example.cb_create_update_product_poc.entity.DocumentAuditHistory;
import com.example.cb_create_update_product_poc.entity.DocumentVersion;
import com.example.cb_create_update_product_poc.exception.InvalidRequestException;
import com.example.cb_create_update_product_poc.repository.DocumentAuditHistoryRepository;
import com.example.cb_create_update_product_poc.repository.DocumentRepository;
import com.example.cb_create_update_product_poc.repository.DocumentVersionRepository;
import com.example.cb_create_update_product_poc.repository.ProductVersionRepository;
import com.example.cb_create_update_product_poc.service.DocumentService;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final DocumentAuditHistoryRepository documentAuditHistoryRepository;
    private final ProductVersionRepository productVersionRepository;
    private final TimeBasedEpochGenerator uuidV7Generator;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               DocumentVersionRepository documentVersionRepository,
                               DocumentAuditHistoryRepository documentAuditHistoryRepository,
                               ProductVersionRepository productVersionRepository,
                               TimeBasedEpochGenerator uuidV7Generator) {
        this.documentRepository = documentRepository;
        this.documentVersionRepository = documentVersionRepository;
        this.documentAuditHistoryRepository = documentAuditHistoryRepository;
        this.productVersionRepository = productVersionRepository;
        this.uuidV7Generator = uuidV7Generator;
    }

    @Override
    @Transactional
    public void addDocumentToProductVersion(UUID productVersionId, DocumentInDto documentInDto, MultipartFile file, UUID userId) {
        // Step 1: Validate that the product version exists
        productVersionRepository.findById(productVersionId)
                .orElseThrow(() -> new InvalidRequestException("ProductVersion with ID " + productVersionId + " not found."));

        // Step 2: Store the file (e.g., to a cloud storage like S3 or a local directory)
        // For this POC, we'll just simulate a storage path.
        String storagePath = "/uploads/" + productVersionId + "/" + file.getOriginalFilename();
        // In a real application, you would add your file storage logic here:
        // s3Service.upload(file, storagePath);

        // Step 3: Create the master Document record
        Document document = new Document();
        document.setId(uuidV7Generator.generate());
        document.setProductVersionId(productVersionId);
        document.setDocumentType(documentInDto.getDocumentType());
        document.setUploadedById(userId);
        document.setStatus("ACTIVE");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());
        Document savedDocument = documentRepository.save(document);

        // Step 4: Create the first DocumentVersion record for this document
        DocumentVersion docVersion = new DocumentVersion();
        docVersion.setId(uuidV7Generator.generate());
        docVersion.setDocumentId(savedDocument.getId());
        docVersion.setVersionNumber(1);
        docVersion.setFileName(file.getOriginalFilename());
        docVersion.setStoragePath(storagePath);
        docVersion.setUploadedById(userId);
        docVersion.setStatus("ACTIVE");
        docVersion.setUploadedAt(LocalDateTime.now());
        docVersion.setComment(documentInDto.getComment());
        DocumentVersion savedDocVersion = documentVersionRepository.save(docVersion);

        // Step 5: Link the latest version back to the master document
        savedDocument.setLatestVersionId(savedDocVersion.getId());
        documentRepository.save(savedDocument);

        // Step 6: Create an audit history record for the upload event
        DocumentAuditHistory auditHistory = new DocumentAuditHistory();
        auditHistory.setId(uuidV7Generator.generate());
        auditHistory.setDocumentId(savedDocument.getId());
        auditHistory.setDocumentVersionId(savedDocVersion.getId());
        auditHistory.setEvent("UPLOADED");
        auditHistory.setPerformedById(userId);
        auditHistory.setComment("Initial document upload.");
        auditHistory.setCreatedAt(LocalDateTime.now());
        documentAuditHistoryRepository.save(auditHistory);
    }
}
