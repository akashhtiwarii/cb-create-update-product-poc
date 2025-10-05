package com.example.cb_create_update_product_poc.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDraftOutDto {

    //Product
    private UUID productId;
    private String name;
    private String productType;
    private UUID latestVersionId;
    private String productStatus;
    private LocalDateTime productCreatedAt;
    private LocalDateTime productUpdatedAt;

    //Product Version
    private UUID productVersionId;
    private Integer versionNumber;
    private String productVersionStatus;
    private String configuration;
    private UUID createdById;
    private UUID approver;
    private LocalDateTime effectiveStartDate;
    private UUID getProductVersionCreatedById;
    private String comment;
}
