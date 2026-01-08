package com.restaurant.system.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for UserType requests
 * Part of Clean Architecture - Application Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    private String descricao;
}
