package com.example.cb_create_update_product_poc.dto.outDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // This annotation prevents null fields from being in the JSON output
public class ApiResponseOutDto<T> {

    private boolean success;
    private String successMessage; // RENAMED for clarity
    private T data;
    private ApiErrorOutDto error;
    private ApiMetaOutDto meta;

    public static <T> ApiResponseOutDto<T> success(T data, String message) {
        return ApiResponseOutDto.<T>builder()
                .success(true)
                .successMessage(message)
                .data(data)
                .error(null)
                .meta(new ApiMetaOutDto(Instant.now()))
                .build();
    }

    public static <T> ApiResponseOutDto<T> failure(String code, String message, Object details) {
        return ApiResponseOutDto.<T>builder()
                .success(false)
                .successMessage(null)
                .data(null)
                .error(new ApiErrorOutDto(code, message, details))
                .meta(new ApiMetaOutDto(Instant.now()))
                .build();
    }
}