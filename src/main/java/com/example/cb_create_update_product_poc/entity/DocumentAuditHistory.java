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
@Table(name = "document_audit_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAuditHistory {

    @Id
    private UUID id;

    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    @Column(name = "document_version_id", nullable = false)
    private UUID documentVersionId;

    @Column(nullable = false)
    private String event;

    @Column(name = "performed_by_id", nullable = false)
    private UUID performedById;

    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}