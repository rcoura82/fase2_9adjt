package com.restaurant.system.infrastructure.persistence.entity;

import com.restaurant.system.domain.model.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * JPA Entity for Appointment
 * Part of Clean Architecture - Infrastructure Layer (Adapter)
 */
@Entity
@Table(name = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long patientId;
    
    @Column(nullable = false)
    private String patientName;
    
    @Column(nullable = false)
    private Long doctorId;
    
    @Column(nullable = false)
    private String doctorName;
    
    @Column(nullable = false)
    private LocalDateTime appointmentDate;
    
    @Column(nullable = false)
    private String specialty;
    
    @Column(length = 1000)
    private String notes;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;
    
    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
    
    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}
