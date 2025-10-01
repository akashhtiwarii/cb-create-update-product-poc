package com.example.cb_create_update_product_poc.repository;

import com.example.cb_create_update_product_poc.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
