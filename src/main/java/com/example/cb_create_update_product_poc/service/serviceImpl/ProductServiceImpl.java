package com.example.cb_create_update_product_poc.service.serviceImpl;

import com.example.cb_create_update_product_poc.dto.inDto.ProductDraftInDto;
import com.example.cb_create_update_product_poc.dto.outDto.ProductDraftOutDto;
import com.example.cb_create_update_product_poc.entity.Product;
import com.example.cb_create_update_product_poc.entity.ProductVersion;
import com.example.cb_create_update_product_poc.entity.WorkflowHistory;
import com.example.cb_create_update_product_poc.exception.InvalidRequestException;
import com.example.cb_create_update_product_poc.mapper.ProductMapper;
import com.example.cb_create_update_product_poc.mapper.ProductVersionMapper;
import com.example.cb_create_update_product_poc.repository.ProductRepository;
import com.example.cb_create_update_product_poc.repository.ProductVersionRepository;
import com.example.cb_create_update_product_poc.repository.WorkflowHistoryRepository;
import com.example.cb_create_update_product_poc.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductVersionRepository productVersionRepository;
    private final WorkflowHistoryRepository workflowHistoryRepository;
    private final TimeBasedEpochGenerator uuidV7Generator;
    private final ObjectMapper objectMapper;
    private final ProductMapper productMapper;
    private final ProductVersionMapper productVersionMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductVersionRepository productVersionRepository,
                              WorkflowHistoryRepository workflowHistoryRepository,
                              TimeBasedEpochGenerator uuidV7Generator,
                              ObjectMapper objectMapper,
                              ProductMapper productMapper, ProductVersionMapper productVersionMapper) {
        this.productRepository = productRepository;
        this.productVersionRepository = productVersionRepository;
        this.workflowHistoryRepository = workflowHistoryRepository;
        this.uuidV7Generator = uuidV7Generator;
        this.objectMapper = objectMapper;
        this.productMapper = productMapper;
        this.productVersionMapper = productVersionMapper;
    }

    @Override
    @Transactional
    public ProductDraftOutDto createProductDraft(ProductDraftInDto productDraftInDto, UUID userId) {
        // Step 1: Create and save the master Product entity
        UUID productId = uuidV7Generator.generate();
        UUID productVersionId = uuidV7Generator.generate();
        Product product = productMapper.productDraftInDtoToProduct(productDraftInDto, productId, userId, "DRAFT");
        Product savedProduct = productRepository.save(product);

        // Step 2: Create the first ProductVersion in DRAFT state
        ProductVersion productVersion = productVersionMapper.productDraftInDtoToProductVersion(productDraftInDto, productVersionId, productId, userId, 1, "DRAFT");

        try {
            String configurationJson = objectMapper.writeValueAsString(productDraftInDto.getConfiguration());
            productVersion.setConfiguration(configurationJson);
        } catch (JsonProcessingException e) {
            throw new InvalidRequestException("Invalid configuration provided.");
        }

        ProductVersion savedProductVersion = productVersionRepository.save(productVersion);

        // Step 3: Link the new version to the master product
        savedProduct.setLatestVersionId(savedProductVersion.getId());
        Product finalProduct = productRepository.save(savedProduct);

        // Step 4: Create the initial entry in the workflow history
        WorkflowHistory history = new WorkflowHistory();
        history.setId(uuidV7Generator.generate());
        history.setProductVersionId(savedProductVersion.getId());
        history.setFromState(null); // No previous state
        history.setToState("DRAFT");
        history.setEvent("CREATE");
        history.setPerformedById(userId);
        history.setCreatedAt(LocalDateTime.now());
        history.setComment(savedProductVersion.getComment());
        workflowHistoryRepository.save(history);

        // Step 5: Map the entities to the OutDto and return
        return productMapper.toProductDraftOutDto(finalProduct, savedProductVersion);
    }
}
