package com.restaurant.system.infrastructure.messaging;

import com.restaurant.system.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for publishing appointment notification events
 * Part of Clean Architecture - Infrastructure Layer
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentNotificationPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    
    public void publishAppointmentNotification(AppointmentNotificationEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.APPOINTMENT_EXCHANGE,
                    RabbitMQConfig.APPOINTMENT_ROUTING_KEY,
                    event
            );
            log.info("Published appointment notification event: {}", event);
        } catch (Exception e) {
            log.error("Error publishing appointment notification event", e);
        }
    }
}
