package com.restaurant.system.infrastructure.persistence.repository;

import com.restaurant.system.domain.model.UserType;
import com.restaurant.system.domain.repository.UserTypeRepository;
import com.restaurant.system.infrastructure.persistence.entity.UserTypeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementing UserTypeRepository using JPA
 * Part of Clean Architecture - Infrastructure Layer (Adapter)
 */
@Component
@RequiredArgsConstructor
public class UserTypeRepositoryImpl implements UserTypeRepository {
    
    private final JpaUserTypeRepository jpaRepository;
    
    @Override
    public UserType save(UserType userType) {
        UserTypeEntity entity = toEntity(userType);
        UserTypeEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<UserType> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
    
    @Override
    public List<UserType> findAll() {
        return jpaRepository.findAll().stream()
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
    
    @Override
    public boolean existsByNome(String nome) {
        return jpaRepository.existsByNome(nome);
    }
    
    private UserTypeEntity toEntity(UserType domain) {
        return UserTypeEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .descricao(domain.getDescricao())
                .criadoEm(domain.getCriadoEm())
                .atualizadoEm(domain.getAtualizadoEm())
                .build();
    }
    
    private UserType toDomain(UserTypeEntity entity) {
        return UserType.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}
