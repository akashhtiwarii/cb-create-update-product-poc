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
@Table(name = "workflow_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowHistory {

    @Id
    private UUID id;

    @Column(name = "product_version_id", nullable = false)
    private UUID productVersionId;

    @Column(name = "from_state")
    private String fromState;

    @Column(name = "to_state", nullable = false)
    private String toState;

    @Column(nullable = false)
    private String event;

    @Column(name = "performed_by_id", nullable = false)
    private UUID performedById;

    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}