package com.restaurant.system.infrastructure.persistence.repository;

import com.restaurant.system.infrastructure.persistence.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for UserTypeEntity
 * Part of Clean Architecture - Infrastructure Layer
 */
@Repository
public interface JpaUserTypeRepository extends JpaRepository<UserTypeEntity, Long> {
    
    boolean existsByNome(String nome);
}
