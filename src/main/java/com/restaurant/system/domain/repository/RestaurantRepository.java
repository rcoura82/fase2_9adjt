package com.restaurant.system.domain.repository;

import com.restaurant.system.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for Restaurant entity
 * Part of Clean Architecture - Domain Layer (Port)
 */
public interface RestaurantRepository {
    
    Restaurant save(Restaurant restaurant);
    
    Optional<Restaurant> findById(Long id);
    
    List<Restaurant> findAll();
    
    List<Restaurant> findByDonoId(Long donoId);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}
