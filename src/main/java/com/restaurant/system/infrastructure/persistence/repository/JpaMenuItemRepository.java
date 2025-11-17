package com.restaurant.system.infrastructure.persistence.repository;

import com.restaurant.system.infrastructure.persistence.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for MenuItemEntity
 * Part of Clean Architecture - Infrastructure Layer
 */
@Repository
public interface JpaMenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
    
    List<MenuItemEntity> findByRestauranteId(Long restauranteId);
}
