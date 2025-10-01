package com.example.cb_create_update_product_poc.repository;

import com.example.cb_create_update_product_poc.entity.WorkflowHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkflowHistoryRepository extends JpaRepository<WorkflowHistory, UUID> {
}
