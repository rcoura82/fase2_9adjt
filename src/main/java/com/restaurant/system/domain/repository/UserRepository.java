package com.restaurant.system.domain.repository;

import com.restaurant.system.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for User entity
 * Part of Clean Architecture - Domain Layer (Port)
 */
public interface UserRepository {
    
    User save(User user);
    
    Optional<User> findById(Long id);
    
    Optional<User> findByUsername(String username);
    
    List<User> findAll();
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
    
    boolean existsByUsername(String username);
}
