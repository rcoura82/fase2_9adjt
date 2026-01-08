package com.restaurant.system.application.dto;

import com.restaurant.system.domain.model.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Appointment
 * Part of Clean Architecture - Application Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    
    private Long id;
    
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    private String patientName;
    
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
    
    private String doctorName;
    
    @NotNull(message = "Appointment date is required")
    private LocalDateTime appointmentDate;
    
    @NotNull(message = "Specialty is required")
    private String specialty;
    
    private String notes;
    
    private AppointmentStatus status;
    
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
