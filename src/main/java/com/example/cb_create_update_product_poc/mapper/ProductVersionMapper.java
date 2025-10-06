package com.example.cb_create_update_product_poc.mapper;

import com.example.cb_create_update_product_poc.dto.inDto.ProductDraftInDto;
import com.example.cb_create_update_product_poc.entity.ProductVersion;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProductVersionMapper {
    public ProductVersion productDraftInDtoToProductVersion(ProductDraftInDto productDraftInDto, UUID productVersionId, UUID productId, UUID userId, int versionNumber, String status) {

        ProductVersion productVersion = new ProductVersion();
        productVersion.setId(productVersionId);
        productVersion.setProductId(productId);
        productVersion.setVersionNumber(versionNumber);
        productVersion.setProductVersionStatus(status);
        productVersion.setCreatedById(userId);
        productVersion.setComment(productDraftInDto.getComment());
        productVersion.setEffectiveStartDate(productDraftInDto.getEffectiveStartDate());
        productVersion.setCreatedAt(LocalDateTime.now());
        productVersion.setUpdatedAt(LocalDateTime.now());
        return productVersion;
    }
}
