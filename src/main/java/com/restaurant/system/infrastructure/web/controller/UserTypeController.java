package com.restaurant.system.infrastructure.web.controller;

import com.restaurant.system.application.dto.UserTypeDTO;
import com.restaurant.system.application.usecase.UserTypeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for UserType operations
 * Part of Clean Architecture - Infrastructure Layer (Web)
 */
@RestController
@RequestMapping("/api/user-types")
@RequiredArgsConstructor
@Tag(name = "User Types", description = "API para gerenciamento de tipos de usuário")
public class UserTypeController {
    
    private final UserTypeUseCase userTypeUseCase;
    
    @PostMapping
    @Operation(summary = "Criar novo tipo de usuário")
    public ResponseEntity<UserTypeDTO> create(@Valid @RequestBody UserTypeDTO dto) {
        UserTypeDTO created = userTypeUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de usuário por ID")
    public ResponseEntity<UserTypeDTO> findById(@PathVariable Long id) {
        UserTypeDTO userType = userTypeUseCase.findById(id);
        return ResponseEntity.ok(userType);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os tipos de usuário")
    public ResponseEntity<List<UserTypeDTO>> findAll() {
        List<UserTypeDTO> userTypes = userTypeUseCase.findAll();
        return ResponseEntity.ok(userTypes);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo de usuário")
    public ResponseEntity<UserTypeDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserTypeDTO dto) {
        UserTypeDTO updated = userTypeUseCase.update(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tipo de usuário")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userTypeUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
