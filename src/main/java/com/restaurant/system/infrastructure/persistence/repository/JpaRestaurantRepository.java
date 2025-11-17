package com.restaurant.system.infrastructure.persistence.repository;

import com.restaurant.system.infrastructure.persistence.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for RestaurantEntity
 * Part of Clean Architecture - Infrastructure Layer
 */
@Repository
public interface JpaRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    
    List<RestaurantEntity> findByDonoId(Long donoId);
}
