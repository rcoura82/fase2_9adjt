package com.restaurant.system.infrastructure.web.controller;

import com.restaurant.system.application.dto.UserDTO;
import com.restaurant.system.application.usecase.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User operations
 * Part of Clean Architecture - Infrastructure Layer (Web)
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API para gerenciamento de usuários")
public class UserController {
    
    private final UserUseCase userUseCase;
    
    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) {
        UserDTO created = userUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO user = userUseCase.findById(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/username/{username}")
    @Operation(summary = "Buscar usuário por username")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        UserDTO user = userUseCase.findByUsername(username);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userUseCase.findAll();
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<UserDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO dto) {
        UserDTO updated = userUseCase.update(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
