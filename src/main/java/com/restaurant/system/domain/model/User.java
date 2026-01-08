package com.restaurant.system.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Domain entity representing a User (Restaurant Owner)
 * Part of Clean Architecture - Domain Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
