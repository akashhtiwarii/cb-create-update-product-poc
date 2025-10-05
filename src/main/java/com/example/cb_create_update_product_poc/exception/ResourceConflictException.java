package com.example.cb_create_update_product_poc.exception;

public class ResourceConflictException extends RuntimeException{
    public ResourceConflictException(final String message) {
        super(message);
    }
}
