package com.restaurant.system.application.usecase;

import com.restaurant.system.application.dto.AppointmentDTO;
import com.restaurant.system.application.exception.ResourceNotFoundException;
import com.restaurant.system.domain.model.Appointment;
import com.restaurant.system.domain.model.AppointmentStatus;
import com.restaurant.system.domain.model.User;
import com.restaurant.system.domain.repository.AppointmentRepository;
import com.restaurant.system.domain.repository.UserRepository;
import com.restaurant.system.infrastructure.messaging.AppointmentNotificationEvent;
import com.restaurant.system.infrastructure.messaging.AppointmentNotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for Appointment operations
 * Part of Clean Architecture - Application Layer
 */
@Service
@RequiredArgsConstructor
public class AppointmentUseCase {
    
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final AppointmentNotificationPublisher notificationPublisher;
    
    @Transactional
    public AppointmentDTO create(AppointmentDTO dto) {
        // Validate patient and doctor exist
        User patient = userRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));
        
        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));
        
        Appointment appointment = Appointment.builder()
                .patientId(dto.getPatientId())
                .patientName(patient.getFullName())
                .doctorId(dto.getDoctorId())
                .doctorName(doctor.getFullName())
                .appointmentDate(dto.getAppointmentDate())
                .specialty(dto.getSpecialty())
                .notes(dto.getNotes())
                .status(AppointmentStatus.SCHEDULED)
                .build();
        
        Appointment saved = appointmentRepository.save(appointment);
        
        // Publish notification event
        AppointmentNotificationEvent event = AppointmentNotificationEvent.builder()
                .appointmentId(saved.getId())
                .patientName(saved.getPatientName())
                .patientEmail(patient.getEmail())
                .doctorName(saved.getDoctorName())
                .appointmentDate(saved.getAppointmentDate())
                .specialty(saved.getSpecialty())
                .eventType("CREATED")
                .build();
        notificationPublisher.publishAppointmentNotification(event);
        
        return toDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public AppointmentDTO findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return toDTO(appointment);
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findFutureAppointmentsByPatientId(Long patientId) {
        LocalDateTime now = LocalDateTime.now();
        return appointmentRepository.findFutureAppointmentsByPatientId(patientId, now).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AppointmentDTO update(Long id, AppointmentDTO dto) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        
        // Get patient for notification
        User patient = userRepository.findById(existing.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + existing.getPatientId()));
        
        // Update fields
        if (dto.getAppointmentDate() != null) {
            existing.setAppointmentDate(dto.getAppointmentDate());
        }
        if (dto.getNotes() != null) {
            existing.setNotes(dto.getNotes());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        
        Appointment updated = appointmentRepository.save(existing);
        
        // Publish notification event
        String eventType = dto.getStatus() == AppointmentStatus.CANCELLED ? "CANCELLED" : "UPDATED";
        AppointmentNotificationEvent event = AppointmentNotificationEvent.builder()
                .appointmentId(updated.getId())
                .patientName(updated.getPatientName())
                .patientEmail(patient.getEmail())
                .doctorName(updated.getDoctorName())
                .appointmentDate(updated.getAppointmentDate())
                .specialty(updated.getSpecialty())
                .eventType(eventType)
                .build();
        notificationPublisher.publishAppointmentNotification(event);
        
        return toDTO(updated);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!appointmentRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }
    
    private AppointmentDTO toDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatientId())
                .patientName(appointment.getPatientName())
                .doctorId(appointment.getDoctorId())
                .doctorName(appointment.getDoctorName())
                .appointmentDate(appointment.getAppointmentDate())
                .specialty(appointment.getSpecialty())
                .notes(appointment.getNotes())
                .status(appointment.getStatus())
                .criadoEm(appointment.getCriadoEm())
                .atualizadoEm(appointment.getAtualizadoEm())
                .build();
    }
}
