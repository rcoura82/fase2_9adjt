package com.restaurant.system.domain.model;

/**
 * Enum representing appointment status
 * Part of Clean Architecture - Domain Layer
 */
public enum AppointmentStatus {
    SCHEDULED("Agendada"),
    CONFIRMED("Confirmada"),
    COMPLETED("Realizada"),
    CANCELLED("Cancelada");
    
    private final String description;
    
    AppointmentStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
