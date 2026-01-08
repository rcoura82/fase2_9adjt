package com.restaurant.system.domain.repository;

import com.restaurant.system.domain.model.MenuItem;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for MenuItem entity
 * Part of Clean Architecture - Domain Layer (Port)
 */
public interface MenuItemRepository {
    
    MenuItem save(MenuItem menuItem);
    
    Optional<MenuItem> findById(Long id);
    
    List<MenuItem> findAll();
    
    List<MenuItem> findByRestauranteId(Long restauranteId);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}
