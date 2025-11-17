package com.restaurant.system.application.exception;

/**
 * Exception thrown when a business rule is violated
 * Part of Clean Architecture - Application Layer
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
}
