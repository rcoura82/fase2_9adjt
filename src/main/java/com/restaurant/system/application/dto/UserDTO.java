package com.restaurant.system.application.dto;

import com.restaurant.system.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for User requests
 * Part of Clean Architecture - Application Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotBlank(message = "Username é obrigatório")
    private String username;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    private String password;
    
    @NotNull(message = "Role é obrigatório")
    private UserRole role;
    
    @NotBlank(message = "Nome completo é obrigatório")
    private String fullName;
    
    private String specialty;  // For doctors
}
