package com.restaurant.system.infrastructure.graphql;

import com.restaurant.system.application.dto.AppointmentDTO;
import com.restaurant.system.application.usecase.AppointmentUseCase;
import com.restaurant.system.domain.model.AppointmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * GraphQL Controller for Appointment queries and mutations
 * Part of Clean Architecture - Infrastructure Layer
 */
@Controller
@RequiredArgsConstructor
public class AppointmentGraphQLController {
    
    private final AppointmentUseCase appointmentUseCase;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    @QueryMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'PATIENT')")
    public AppointmentDTO appointment(@Argument Long id) {
        return appointmentUseCase.findById(id);
    }
    
    @QueryMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    public List<AppointmentDTO> appointments() {
        return appointmentUseCase.findAll();
    }
    
    @QueryMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'PATIENT')")
    public List<AppointmentDTO> appointmentsByPatient(@Argument Long patientId) {
        return appointmentUseCase.findByPatientId(patientId);
    }
    
    @QueryMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'PATIENT')")
    public List<AppointmentDTO> futureAppointmentsByPatient(@Argument Long patientId) {
        return appointmentUseCase.findFutureAppointmentsByPatientId(patientId);
    }
    
    @QueryMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    public List<AppointmentDTO> appointmentsByDoctor(@Argument Long doctorId) {
        return appointmentUseCase.findByDoctorId(doctorId);
    }
    
    @MutationMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    public AppointmentDTO createAppointment(@Argument Map<String, Object> input) {
        AppointmentDTO dto = AppointmentDTO.builder()
                .patientId(Long.valueOf(input.get("patientId").toString()))
                .doctorId(Long.valueOf(input.get("doctorId").toString()))
                .appointmentDate(LocalDateTime.parse(input.get("appointmentDate").toString(), formatter))
                .specialty(input.get("specialty").toString())
                .notes(input.get("notes") != null ? input.get("notes").toString() : null)
                .build();
        
        return appointmentUseCase.create(dto);
    }
    
    @MutationMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    public AppointmentDTO updateAppointment(@Argument Long id, @Argument Map<String, Object> input) {
        AppointmentDTO dto = AppointmentDTO.builder().build();
        
        if (input.containsKey("appointmentDate")) {
            dto.setAppointmentDate(LocalDateTime.parse(input.get("appointmentDate").toString(), formatter));
        }
        if (input.containsKey("notes")) {
            dto.setNotes(input.get("notes").toString());
        }
        if (input.containsKey("status")) {
            dto.setStatus(AppointmentStatus.valueOf(input.get("status").toString()));
        }
        
        return appointmentUseCase.update(id, dto);
    }
    
    @MutationMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    public Boolean deleteAppointment(@Argument Long id) {
        appointmentUseCase.delete(id);
        return true;
    }
}
