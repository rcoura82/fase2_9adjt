package com.restaurant.system.infrastructure.persistence.repository;

import com.restaurant.system.infrastructure.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA Repository for AppointmentEntity
 * Part of Clean Architecture - Infrastructure Layer
 */
@Repository
public interface JpaAppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    
    List<AppointmentEntity> findByPatientId(Long patientId);
    
    List<AppointmentEntity> findByDoctorId(Long doctorId);
    
    @Query("SELECT a FROM AppointmentEntity a WHERE a.patientId = :patientId AND a.appointmentDate >= :fromDate ORDER BY a.appointmentDate ASC")
    List<AppointmentEntity> findFutureAppointmentsByPatientId(@Param("patientId") Long patientId, @Param("fromDate") LocalDateTime fromDate);
}
