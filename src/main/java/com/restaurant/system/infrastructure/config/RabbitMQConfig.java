package com.restaurant.system.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Configuration
 * Part of Clean Architecture - Infrastructure Layer
 */
@Configuration
public class RabbitMQConfig {
    
    public static final String APPOINTMENT_EXCHANGE = "appointment.exchange";
    public static final String APPOINTMENT_QUEUE = "appointment.queue";
    public static final String APPOINTMENT_ROUTING_KEY = "appointment.notification";
    
    @Bean
    public Queue appointmentQueue() {
        return new Queue(APPOINTMENT_QUEUE, true);
    }
    
    @Bean
    public TopicExchange appointmentExchange() {
        return new TopicExchange(APPOINTMENT_EXCHANGE);
    }
    
    @Bean
    public Binding appointmentBinding(Queue appointmentQueue, TopicExchange appointmentExchange) {
        return BindingBuilder.bind(appointmentQueue)
                .to(appointmentExchange)
                .with(APPOINTMENT_ROUTING_KEY);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
