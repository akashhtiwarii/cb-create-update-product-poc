package com.example.cb_create_update_product_poc.mapper;

import com.example.cb_create_update_product_poc.dto.inDto.ProductDraftInDto;
import com.example.cb_create_update_product_poc.dto.outDto.ProductDraftOutDto;
import com.example.cb_create_update_product_poc.entity.Product;
import com.example.cb_create_update_product_poc.entity.ProductVersion;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductMapper {
    public ProductDraftOutDto toProductDraftOutDto(Product product, ProductVersion productVersion) {
        if (product == null || productVersion == null) {
            return null;
        }

        ProductDraftOutDto dto = new ProductDraftOutDto();

        // --- Map Product fields ---
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setProductType(product.getProductType());
        dto.setLatestVersionId(product.getLatestVersionId());
        dto.setProductStatus(product.getStatus());
        dto.setProductCreatedAt(product.getCreatedAt());
        dto.setProductUpdatedAt(product.getUpdatedAt());

        // --- Map ProductVersion fields ---
        dto.setProductVersionId(productVersion.getId());
        dto.setVersionNumber(productVersion.getVersionNumber());
        dto.setProductVersionStatus(productVersion.getProductVersionStatus());
        dto.setConfiguration(productVersion.getConfiguration());
        dto.setCreatedById(productVersion.getCreatedById());
        dto.setApprover(productVersion.getApprover());
        dto.setEffectiveStartDate(productVersion.getEffectiveStartDate());
        dto.setComment(productVersion.getComment());

        return dto;
    }
}
