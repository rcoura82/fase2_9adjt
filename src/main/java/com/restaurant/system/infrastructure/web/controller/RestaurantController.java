package com.restaurant.system.infrastructure.web.controller;

import com.restaurant.system.application.dto.RestaurantDTO;
import com.restaurant.system.application.usecase.RestaurantUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Restaurant operations
 * Part of Clean Architecture - Infrastructure Layer (Web)
 */
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurants", description = "API para gerenciamento de restaurantes")
public class RestaurantController {
    
    private final RestaurantUseCase restaurantUseCase;
    
    @PostMapping
    @Operation(summary = "Criar novo restaurante")
    public ResponseEntity<RestaurantDTO> create(@Valid @RequestBody RestaurantDTO dto) {
        RestaurantDTO created = restaurantUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID")
    public ResponseEntity<RestaurantDTO> findById(@PathVariable Long id) {
        RestaurantDTO restaurant = restaurantUseCase.findById(id);
        return ResponseEntity.ok(restaurant);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os restaurantes")
    public ResponseEntity<List<RestaurantDTO>> findAll() {
        List<RestaurantDTO> restaurants = restaurantUseCase.findAll();
        return ResponseEntity.ok(restaurants);
    }
    
    @GetMapping("/owner/{donoId}")
    @Operation(summary = "Listar restaurantes por dono")
    public ResponseEntity<List<RestaurantDTO>> findByDonoId(@PathVariable Long donoId) {
        List<RestaurantDTO> restaurants = restaurantUseCase.findByDonoId(donoId);
        return ResponseEntity.ok(restaurants);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante")
    public ResponseEntity<RestaurantDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantDTO dto) {
        RestaurantDTO updated = restaurantUseCase.update(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar restaurante")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
