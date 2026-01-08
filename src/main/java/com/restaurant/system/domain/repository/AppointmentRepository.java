package com.restaurant.system.domain.repository;

import com.restaurant.system.domain.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Appointment
 * Part of Clean Architecture - Domain Layer (Port)
 */
public interface AppointmentRepository {
    
    Appointment save(Appointment appointment);
    
    Optional<Appointment> findById(Long id);
    
    List<Appointment> findAll();
    
    List<Appointment> findByPatientId(Long patientId);
    
    List<Appointment> findByDoctorId(Long doctorId);
    
    List<Appointment> findFutureAppointmentsByPatientId(Long patientId, LocalDateTime fromDate);
    
    void deleteById(Long id);
}
