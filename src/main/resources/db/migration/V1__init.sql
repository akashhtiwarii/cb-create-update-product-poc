-- V1__init.sql
-- Flyway migration script to create Product Management schema

-- 5.1 Product Table
CREATE TABLE product (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    product_type VARCHAR(50) NOT NULL, -- e.g., UNSECURED_CARD, LOAN, etc.
    latest_version_id UUID,            -- references latest published version
    status VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 5.2 ProductVersion Table
CREATE TABLE product_version (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    version_number INT NOT NULL,
    status VARCHAR(30) NOT NULL CHECK (
        status IN ('DRAFT','SUBMITTED_FOR_REVIEW','REJECTED','APPROVED','PUBLISHED','ARCHIVED')
    ),
    configuration JSONB NOT NULL, -- Postgres JSONB for flexibility
    created_by_id UUID NOT NULL,
    approver UUID,
    effective_start_date TIMESTAMP,
    published_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comment TEXT,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

-- update product.latest_version_id FK after product_version exists
ALTER TABLE product
    ADD CONSTRAINT fk_product_latest_version FOREIGN KEY (latest_version_id)
    REFERENCES product_version (id);

-- 5.3 WorkflowHistory Table
CREATE TABLE workflow_history (
    id UUID PRIMARY KEY,
    product_version_id UUID NOT NULL,
    from_state VARCHAR(30),
    to_state VARCHAR(30) NOT NULL,
    event VARCHAR(20) NOT NULL CHECK (
        event IN ('CREATE','SUBMIT','APPROVE','REJECT','PUBLISH','ARCHIVE','REWORK','REACTIVATE')
    ),
    performed_by_id UUID NOT NULL,
    comment TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_workflow_product_version FOREIGN KEY (product_version_id)
        REFERENCES product_version (id) ON DELETE CASCADE
);

-- 5.4 Document Table
CREATE TABLE document (
    id UUID PRIMARY KEY,
    product_version_id UUID NOT NULL,
    document_type VARCHAR(100) NOT NULL,
    latest_version_id UUID,
    uploaded_by_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_document_product_version FOREIGN KEY (product_version_id)
        REFERENCES product_version (id) ON DELETE CASCADE
);

-- 5.5 DocumentVersion Table
CREATE TABLE document_version (
    id UUID PRIMARY KEY,
    document_id UUID NOT NULL,
    version_number INT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    storage_path VARCHAR(500) NOT NULL,
    uploaded_by_id UUID NOT NULL,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comment TEXT,
    CONSTRAINT fk_document FOREIGN KEY (document_id) REFERENCES document (id) ON DELETE CASCADE
);

-- update document.latest_version_id FK after document_version exists
ALTER TABLE document
    ADD CONSTRAINT fk_document_latest_version FOREIGN KEY (latest_version_id)
    REFERENCES document_version (id);
