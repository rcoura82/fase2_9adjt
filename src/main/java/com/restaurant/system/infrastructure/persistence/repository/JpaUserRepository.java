package com.restaurant.system.infrastructure.persistence.repository;

import com.restaurant.system.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for UserEntity
 * Part of Clean Architecture - Infrastructure Layer
 */
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByUsername(String username);
    
    boolean existsByUsername(String username);
}
