package com.restaurant.system.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for Restaurant requests
 * Part of Clean Architecture - Application Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;
    
    @NotBlank(message = "Tipo de cozinha é obrigatório")
    private String tipoCozinha;
    
    @NotBlank(message = "Horário de funcionamento é obrigatório")
    private String horarioFuncionamento;
    
    private Long donoId;
    private String donoNome;
}
