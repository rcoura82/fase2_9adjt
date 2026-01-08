package com.restaurant.system.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Domain entity representing an Appointment
 * Part of Clean Architecture - Domain Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    
    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private String specialty;
    private String notes;
    private AppointmentStatus status;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
