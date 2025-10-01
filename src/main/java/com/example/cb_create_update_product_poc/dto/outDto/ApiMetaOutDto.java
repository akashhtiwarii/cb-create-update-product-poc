package com.example.cb_create_update_product_poc.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiMetaOutDto {

    private Instant timestamp;
}
