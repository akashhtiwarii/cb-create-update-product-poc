package com.example.cb_create_update_product_poc.repository;

import com.example.cb_create_update_product_poc.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, UUID> {
}
