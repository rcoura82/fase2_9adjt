package com.restaurant.system.infrastructure.web.controller;

import com.restaurant.system.application.dto.MenuItemDTO;
import com.restaurant.system.application.usecase.MenuItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for MenuItem operations
 * Part of Clean Architecture - Infrastructure Layer (Web)
 */
@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
@Tag(name = "Menu Items", description = "API para gerenciamento de itens do cardápio")
public class MenuItemController {
    
    private final MenuItemUseCase menuItemUseCase;
    
    @PostMapping
    @Operation(summary = "Criar novo item do cardápio")
    public ResponseEntity<MenuItemDTO> create(@Valid @RequestBody MenuItemDTO dto) {
        MenuItemDTO created = menuItemUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar item do cardápio por ID")
    public ResponseEntity<MenuItemDTO> findById(@PathVariable Long id) {
        MenuItemDTO menuItem = menuItemUseCase.findById(id);
        return ResponseEntity.ok(menuItem);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os itens do cardápio")
    public ResponseEntity<List<MenuItemDTO>> findAll() {
        List<MenuItemDTO> menuItems = menuItemUseCase.findAll();
        return ResponseEntity.ok(menuItems);
    }
    
    @GetMapping("/restaurant/{restauranteId}")
    @Operation(summary = "Listar itens do cardápio por restaurante")
    public ResponseEntity<List<MenuItemDTO>> findByRestauranteId(@PathVariable Long restauranteId) {
        List<MenuItemDTO> menuItems = menuItemUseCase.findByRestauranteId(restauranteId);
        return ResponseEntity.ok(menuItems);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item do cardápio")
    public ResponseEntity<MenuItemDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemDTO dto) {
        MenuItemDTO updated = menuItemUseCase.update(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar item do cardápio")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuItemUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
