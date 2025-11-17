package com.restaurant.system.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain entity representing a Menu Item
 * Part of Clean Architecture - Domain Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponivelApenasRestaurante;
    private String fotoCaminho;
    private Long restauranteId;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
