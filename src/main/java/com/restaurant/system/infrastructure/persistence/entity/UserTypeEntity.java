package com.restaurant.system.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity for UserType
 * Part of Clean Architecture - Infrastructure Layer
 */
@Entity
@Table(name = "user_types")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nome;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
