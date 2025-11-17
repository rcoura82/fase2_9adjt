package com.restaurant.system.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity for Restaurant
 * Part of Clean Architecture - Infrastructure Layer
 */
@Entity
@Table(name = "restaurants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String endereco;
    
    @Column(nullable = false)
    private String tipoCozinha;
    
    @Column(nullable = false)
    private String horarioFuncionamento;
    
    @Column(nullable = false)
    private Long donoId;
    
    private String donoNome;
    
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MenuItemEntity> itensCardapio = new ArrayList<>();
    
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
