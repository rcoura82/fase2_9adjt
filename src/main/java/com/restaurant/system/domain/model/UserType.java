package com.restaurant.system.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Domain entity representing a User Type
 * Part of Clean Architecture - Domain Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserType {
    
    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
