package com.restaurant.system.application.usecase;

import com.restaurant.system.application.dto.UserTypeDTO;
import com.restaurant.system.application.exception.BusinessException;
import com.restaurant.system.application.exception.ResourceNotFoundException;
import com.restaurant.system.domain.model.UserType;
import com.restaurant.system.domain.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for UserType operations
 * Part of Clean Architecture - Application Layer
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserTypeUseCase {
    
    private final UserTypeRepository userTypeRepository;
    
    public UserTypeDTO create(UserTypeDTO dto) {
        if (userTypeRepository.existsByNome(dto.getNome())) {
            throw new BusinessException("Tipo de usuário já existe: " + dto.getNome());
        }
        
        UserType userType = UserType.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
        
        UserType saved = userTypeRepository.save(userType);
        return toDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public UserTypeDTO findById(Long id) {
        UserType userType = userTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário", id));
        return toDTO(userType);
    }
    
    @Transactional(readOnly = true)
    public List<UserTypeDTO> findAll() {
        return userTypeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public UserTypeDTO update(Long id, UserTypeDTO dto) {
        UserType existing = userTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário", id));
        
        if (!existing.getNome().equals(dto.getNome()) && 
            userTypeRepository.existsByNome(dto.getNome())) {
            throw new BusinessException("Tipo de usuário já existe: " + dto.getNome());
        }
        
        existing.setNome(dto.getNome());
        existing.setDescricao(dto.getDescricao());
        existing.setAtualizadoEm(LocalDateTime.now());
        
        UserType updated = userTypeRepository.save(existing);
        return toDTO(updated);
    }
    
    public void delete(Long id) {
        if (!userTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tipo de usuário", id);
        }
        userTypeRepository.deleteById(id);
    }
    
    private UserTypeDTO toDTO(UserType userType) {
        return UserTypeDTO.builder()
                .id(userType.getId())
                .nome(userType.getNome())
                .descricao(userType.getDescricao())
                .build();
    }
}
