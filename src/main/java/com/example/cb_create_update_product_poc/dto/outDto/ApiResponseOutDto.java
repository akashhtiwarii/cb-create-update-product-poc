package com.example.cb_create_update_product_poc.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseOutDto<T> {

    private boolean success;
    private T data;
    private ApiErrorOutDto error;
    private ApiMetaOutDto meta;

    public static <T> ApiResponseOutDto<T> success(T data) {
        return ApiResponseOutDto.<T>builder()
                .success(true)
                .data(data)
                .error(null)
                .meta(new ApiMetaOutDto(Instant.now()))
                .build();
    }

    public static <T> ApiResponseOutDto<T> failure(String code, String message, Object details) {
        return ApiResponseOutDto.<T>builder()
                .success(false)
                .data(null)
                .error(new ApiErrorOutDto(code, message, details))
                .meta(new ApiMetaOutDto(Instant.now()))
                .build();
    }
}