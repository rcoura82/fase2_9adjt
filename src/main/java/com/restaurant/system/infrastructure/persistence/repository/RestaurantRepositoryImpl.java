package com.restaurant.system.infrastructure.persistence.repository;

import com.restaurant.system.domain.model.Restaurant;
import com.restaurant.system.domain.repository.RestaurantRepository;
import com.restaurant.system.infrastructure.persistence.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementing RestaurantRepository using JPA
 * Part of Clean Architecture - Infrastructure Layer (Adapter)
 */
@Component
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {
    
    private final JpaRestaurantRepository jpaRepository;
    
    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity entity = toEntity(restaurant);
        RestaurantEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<Restaurant> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
    
    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Restaurant> findByDonoId(Long donoId) {
        return jpaRepository.findByDonoId(donoId).stream()
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
    
    private RestaurantEntity toEntity(Restaurant domain) {
        return RestaurantEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .endereco(domain.getEndereco())
                .tipoCozinha(domain.getTipoCozinha())
                .horarioFuncionamento(domain.getHorarioFuncionamento())
                .donoId(domain.getDonoId())
                .donoNome(domain.getDonoNome())
                .criadoEm(domain.getCriadoEm())
                .atualizadoEm(domain.getAtualizadoEm())
                .build();
    }
    
    private Restaurant toDomain(RestaurantEntity entity) {
        return Restaurant.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .endereco(entity.getEndereco())
                .tipoCozinha(entity.getTipoCozinha())
                .horarioFuncionamento(entity.getHorarioFuncionamento())
                .donoId(entity.getDonoId())
                .donoNome(entity.getDonoNome())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}
