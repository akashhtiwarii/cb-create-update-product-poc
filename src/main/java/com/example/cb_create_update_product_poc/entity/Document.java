package com.example.cb_create_update_product_poc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    private UUID id;

    @Column(name = "product_version_id", nullable = false)
    private UUID productVersionId;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "latest_version_id")
    private UUID latestVersionId;

    @Column(name = "uploaded_by_id", nullable = false)
    private UUID uploadedById;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}