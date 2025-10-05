package com.example.cb_create_update_product_poc.exception;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(final String message) {
        super(message);
    }
}
