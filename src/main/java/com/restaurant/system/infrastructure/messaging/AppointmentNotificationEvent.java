package com.restaurant.system.infrastructure.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for appointment notification events
 * Part of Clean Architecture - Infrastructure Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentNotificationEvent {
    
    private Long appointmentId;
    private String patientName;
    private String patientEmail;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private String specialty;
    private String eventType;  // CREATED, UPDATED, CANCELLED
}
