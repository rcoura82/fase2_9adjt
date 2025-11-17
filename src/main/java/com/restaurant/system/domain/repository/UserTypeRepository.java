package com.restaurant.system.domain.repository;

import com.restaurant.system.domain.model.UserType;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for UserType entity
 * Part of Clean Architecture - Domain Layer (Port)
 */
public interface UserTypeRepository {
    
    UserType save(UserType userType);
    
    Optional<UserType> findById(Long id);
    
    List<UserType> findAll();
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
    
    boolean existsByNome(String nome);
}
