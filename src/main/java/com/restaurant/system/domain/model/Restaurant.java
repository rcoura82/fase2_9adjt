package com.restaurant.system.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain entity representing a Restaurant
 * Part of Clean Architecture - Domain Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    
    private Long id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private Long donoId;
    private String donoNome;
    
    @Builder.Default
    private List<MenuItem> itensCardapio = new ArrayList<>();
    
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    public void adicionarItemCardapio(MenuItem item) {
        if (itensCardapio == null) {
            itensCardapio = new ArrayList<>();
        }
        itensCardapio.add(item);
    }
    
    public void removerItemCardapio(MenuItem item) {
        if (itensCardapio != null) {
            itensCardapio.remove(item);
        }
    }
}
