package com.restaurant.system.application.usecase;

import com.restaurant.system.application.dto.AppointmentDTO;
import com.restaurant.system.domain.model.Appointment;
import com.restaurant.system.domain.model.AppointmentStatus;
import com.restaurant.system.domain.model.User;
import com.restaurant.system.domain.model.UserRole;
import com.restaurant.system.domain.repository.AppointmentRepository;
import com.restaurant.system.domain.repository.UserRepository;
import com.restaurant.system.infrastructure.messaging.AppointmentNotificationPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AppointmentUseCase
 */
@ExtendWith(MockitoExtension.class)
class AppointmentUseCaseTest {
    
    @Mock
    private AppointmentRepository appointmentRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private AppointmentNotificationPublisher notificationPublisher;
    
    @InjectMocks
    private AppointmentUseCase appointmentUseCase;
    
    private User patient;
    private User doctor;
    private AppointmentDTO appointmentDTO;
    private Appointment appointment;
    
    @BeforeEach
    void setUp() {
        patient = User.builder()
                .id(1L)
                .username("patient1")
                .email("patient@test.com")
                .password("password")
                .role(UserRole.PATIENT)
                .fullName("Patient Test")
                .build();
        
        doctor = User.builder()
                .id(2L)
                .username("doctor1")
                .email("doctor@test.com")
                .password("password")
                .role(UserRole.DOCTOR)
                .fullName("Doctor Test")
                .specialty("Cardiology")
                .build();
        
        appointmentDTO = AppointmentDTO.builder()
                .patientId(1L)
                .doctorId(2L)
                .appointmentDate(LocalDateTime.now().plusDays(1))
                .specialty("Cardiology")
                .notes("Regular checkup")
                .build();
        
        appointment = Appointment.builder()
                .id(1L)
                .patientId(1L)
                .patientName("Patient Test")
                .doctorId(2L)
                .doctorName("Doctor Test")
                .appointmentDate(appointmentDTO.getAppointmentDate())
                .specialty("Cardiology")
                .notes("Regular checkup")
                .status(AppointmentStatus.SCHEDULED)
                .build();
    }
    
    @Test
    void testCreateAppointment() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(userRepository.findById(2L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        
        AppointmentDTO result = appointmentUseCase.create(appointmentDTO);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Patient Test", result.getPatientName());
        assertEquals("Doctor Test", result.getDoctorName());
        assertEquals(AppointmentStatus.SCHEDULED, result.getStatus());
        
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(notificationPublisher, times(1)).publishAppointmentNotification(any());
    }
    
    @Test
    void testFindById() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        
        AppointmentDTO result = appointmentUseCase.findById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Patient Test", result.getPatientName());
        
        verify(appointmentRepository, times(1)).findById(1L);
    }
}
