package com.restaurant.system.application.exception;

/**
 * Exception thrown when a resource is not found
 * Part of Clean Architecture - Application Layer
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado com id: %d", resourceName, id));
    }
}
