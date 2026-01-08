package com.restaurant.system.infrastructure.persistence.repository;

import com.restaurant.system.domain.model.Appointment;
import com.restaurant.system.domain.repository.AppointmentRepository;
import com.restaurant.system.infrastructure.persistence.entity.AppointmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of AppointmentRepository
 * Part of Clean Architecture - Infrastructure Layer (Adapter)
 */
@Component
@RequiredArgsConstructor
public class AppointmentRepositoryImpl implements AppointmentRepository {
    
    private final JpaAppointmentRepository jpaRepository;
    
    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = toEntity(appointment);
        AppointmentEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<Appointment> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
    
    @Override
    public List<Appointment> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findByPatientId(Long patientId) {
        return jpaRepository.findByPatientId(patientId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findByDoctorId(Long doctorId) {
        return jpaRepository.findByDoctorId(doctorId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findFutureAppointmentsByPatientId(Long patientId, LocalDateTime fromDate) {
        return jpaRepository.findFutureAppointmentsByPatientId(patientId, fromDate).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    private AppointmentEntity toEntity(Appointment domain) {
        return AppointmentEntity.builder()
                .id(domain.getId())
                .patientId(domain.getPatientId())
                .patientName(domain.getPatientName())
                .doctorId(domain.getDoctorId())
                .doctorName(domain.getDoctorName())
                .appointmentDate(domain.getAppointmentDate())
                .specialty(domain.getSpecialty())
                .notes(domain.getNotes())
                .status(domain.getStatus())
                .criadoEm(domain.getCriadoEm())
                .atualizadoEm(domain.getAtualizadoEm())
                .build();
    }
    
    private Appointment toDomain(AppointmentEntity entity) {
        return Appointment.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .patientName(entity.getPatientName())
                .doctorId(entity.getDoctorId())
                .doctorName(entity.getDoctorName())
                .appointmentDate(entity.getAppointmentDate())
                .specialty(entity.getSpecialty())
                .notes(entity.getNotes())
                .status(entity.getStatus())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}
