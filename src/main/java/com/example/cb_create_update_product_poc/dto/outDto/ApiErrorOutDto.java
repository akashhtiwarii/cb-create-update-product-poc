package com.example.cb_create_update_product_poc.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorOutDto {

    private String code;
    private String message;
    private Object details;
}