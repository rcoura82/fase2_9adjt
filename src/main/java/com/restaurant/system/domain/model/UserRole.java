package com.restaurant.system.domain.model;

/**
 * Enum representing user roles in the hospital system
 * Part of Clean Architecture - Domain Layer
 */
public enum UserRole {
    PATIENT("Paciente"),
    NURSE("Enfermeiro"),
    DOCTOR("Médico");
    
    private final String description;
    
    UserRole(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
