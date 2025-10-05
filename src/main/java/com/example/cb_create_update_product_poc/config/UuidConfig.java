package com.example.cb_create_update_product_poc.config;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UuidConfig {

    @Bean
    public TimeBasedEpochGenerator uuidV7Generator() {
        return Generators.timeBasedEpochGenerator();
    }
}
