package com.restaurant.system.infrastructure.messaging;

import com.restaurant.system.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Consumer for appointment notification events
 * Part of Clean Architecture - Infrastructure Layer
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentNotificationConsumer {
    
    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_QUEUE)
    public void handleAppointmentNotification(AppointmentNotificationEvent event) {
        log.info("Received appointment notification event: {}", event);
        
        // Simulate sending notification
        try {
            sendNotification(event);
            log.info("Successfully sent notification for appointment ID: {}", event.getAppointmentId());
        } catch (Exception e) {
            log.error("Error sending notification for appointment ID: " + event.getAppointmentId(), e);
        }
    }
    
    private void sendNotification(AppointmentNotificationEvent event) {
        // In a real implementation, this would send email/SMS
        String message = String.format(
                "Notification sent to %s:\nAppointment with Dr. %s on %s\nSpecialty: %s\nEvent: %s",
                event.getPatientName(),
                event.getDoctorName(),
                event.getAppointmentDate(),
                event.getSpecialty(),
                event.getEventType()
        );
        log.info(message);
    }
}
