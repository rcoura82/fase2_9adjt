package com.restaurant.system.application.usecase;

import com.restaurant.system.application.dto.UserDTO;
import com.restaurant.system.application.exception.BusinessException;
import com.restaurant.system.application.exception.ResourceNotFoundException;
import com.restaurant.system.domain.model.User;
import com.restaurant.system.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for User operations
 * Part of Clean Architecture - Application Layer
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserDTO create(UserDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Username já existe: " + dto.getUsername());
        }
        
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .fullName(dto.getFullName())
                .specialty(dto.getSpecialty())
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
        
        User saved = userRepository.save(user);
        return toDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
        return toDTO(user);
    }
    
    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + username));
        return toDTO(user);
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public UserDTO update(Long id, UserDTO dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
        
        if (!existing.getUsername().equals(dto.getUsername()) && 
            userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Username já existe: " + dto.getUsername());
        }
        
        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        existing.setRole(dto.getRole());
        existing.setFullName(dto.getFullName());
        existing.setSpecialty(dto.getSpecialty());
        existing.setAtualizadoEm(LocalDateTime.now());
        
        User updated = userRepository.save(existing);
        return toDTO(updated);
    }
    
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário", id);
        }
        userRepository.deleteById(id);
    }
    
    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .fullName(user.getFullName())
                .specialty(user.getSpecialty())
                // Don't return password in DTO
                .build();
    }
}
