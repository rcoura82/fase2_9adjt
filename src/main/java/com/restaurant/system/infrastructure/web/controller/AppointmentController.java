package com.restaurant.system.infrastructure.web.controller;

import com.restaurant.system.application.dto.AppointmentDTO;
import com.restaurant.system.application.usecase.AppointmentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Appointment operations
 * Part of Clean Architecture - Infrastructure Layer (Web)
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "API para gerenciamento de consultas")
@SecurityRequirement(name = "basicAuth")
public class AppointmentController {
    
    private final AppointmentUseCase appointmentUseCase;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @Operation(summary = "Criar nova consulta (apenas médicos e enfermeiros)")
    public ResponseEntity<AppointmentDTO> create(@Valid @RequestBody AppointmentDTO dto) {
        AppointmentDTO created = appointmentUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'PATIENT')")
    @Operation(summary = "Buscar consulta por ID")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
        AppointmentDTO appointment = appointmentUseCase.findById(id);
        return ResponseEntity.ok(appointment);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @Operation(summary = "Listar todas as consultas (apenas médicos e enfermeiros)")
    public ResponseEntity<List<AppointmentDTO>> findAll() {
        List<AppointmentDTO> appointments = appointmentUseCase.findAll();
        return ResponseEntity.ok(appointments);
    }
    
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'PATIENT')")
    @Operation(summary = "Listar consultas por paciente")
    public ResponseEntity<List<AppointmentDTO>> findByPatientId(@PathVariable Long patientId) {
        List<AppointmentDTO> appointments = appointmentUseCase.findByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }
    
    @GetMapping("/patient/{patientId}/future")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'PATIENT')")
    @Operation(summary = "Listar consultas futuras por paciente")
    public ResponseEntity<List<AppointmentDTO>> findFutureAppointmentsByPatientId(@PathVariable Long patientId) {
        List<AppointmentDTO> appointments = appointmentUseCase.findFutureAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }
    
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @Operation(summary = "Listar consultas por médico")
    public ResponseEntity<List<AppointmentDTO>> findByDoctorId(@PathVariable Long doctorId) {
        List<AppointmentDTO> appointments = appointmentUseCase.findByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @Operation(summary = "Atualizar consulta (apenas médicos e enfermeiros)")
    public ResponseEntity<AppointmentDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentDTO dto) {
        AppointmentDTO updated = appointmentUseCase.update(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @Operation(summary = "Deletar consulta (apenas médicos e enfermeiros)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
