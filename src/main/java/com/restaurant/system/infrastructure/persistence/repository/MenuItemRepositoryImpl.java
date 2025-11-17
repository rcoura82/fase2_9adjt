package com.restaurant.system.infrastructure.persistence.repository;

import com.restaurant.system.domain.model.MenuItem;
import com.restaurant.system.domain.repository.MenuItemRepository;
import com.restaurant.system.infrastructure.persistence.entity.MenuItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementing MenuItemRepository using JPA
 * Part of Clean Architecture - Infrastructure Layer (Adapter)
 */
@Component
@RequiredArgsConstructor
public class MenuItemRepositoryImpl implements MenuItemRepository {
    
    private final JpaMenuItemRepository jpaRepository;
    private final JpaRestaurantRepository jpaRestaurantRepository;
    
    @Override
    public MenuItem save(MenuItem menuItem) {
        MenuItemEntity entity = toEntity(menuItem);
        MenuItemEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<MenuItem> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
    
    @Override
    public List<MenuItem> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MenuItem> findByRestauranteId(Long restauranteId) {
        return jpaRepository.findByRestauranteId(restauranteId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
    
    private MenuItemEntity toEntity(MenuItem domain) {
        MenuItemEntity entity = MenuItemEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .descricao(domain.getDescricao())
                .preco(domain.getPreco())
                .disponivelApenasRestaurante(domain.getDisponivelApenasRestaurante())
                .fotoCaminho(domain.getFotoCaminho())
                .restauranteId(domain.getRestauranteId())
                .criadoEm(domain.getCriadoEm())
                .atualizadoEm(domain.getAtualizadoEm())
                .build();
        
        if (domain.getRestauranteId() != null) {
            jpaRestaurantRepository.findById(domain.getRestauranteId())
                    .ifPresent(entity::setRestaurante);
        }
        
        return entity;
    }
    
    private MenuItem toDomain(MenuItemEntity entity) {
        return MenuItem.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .preco(entity.getPreco())
                .disponivelApenasRestaurante(entity.getDisponivelApenasRestaurante())
                .fotoCaminho(entity.getFotoCaminho())
                .restauranteId(entity.getRestauranteId())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}
