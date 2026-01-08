package com.restaurant.system.application.usecase;

import com.restaurant.system.application.dto.RestaurantDTO;
import com.restaurant.system.application.exception.ResourceNotFoundException;
import com.restaurant.system.domain.model.Restaurant;
import com.restaurant.system.domain.model.User;
import com.restaurant.system.domain.repository.RestaurantRepository;
import com.restaurant.system.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for Restaurant operations
 * Part of Clean Architecture - Application Layer
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantUseCase {
    
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    
    public RestaurantDTO create(RestaurantDTO dto) {
        User dono = userRepository.findById(dto.getDonoId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", dto.getDonoId()));
        
        Restaurant restaurant = Restaurant.builder()
                .nome(dto.getNome())
                .endereco(dto.getEndereco())
                .tipoCozinha(dto.getTipoCozinha())
                .horarioFuncionamento(dto.getHorarioFuncionamento())
                .donoId(dto.getDonoId())
                .donoNome(dono.getUsername())
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
        
        Restaurant saved = restaurantRepository.save(restaurant);
        return toDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public RestaurantDTO findById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", id));
        return toDTO(restaurant);
    }
    
    @Transactional(readOnly = true)
    public List<RestaurantDTO> findAll() {
        return restaurantRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<RestaurantDTO> findByDonoId(Long donoId) {
        return restaurantRepository.findByDonoId(donoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public RestaurantDTO update(Long id, RestaurantDTO dto) {
        Restaurant existing = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", id));
        
        existing.setNome(dto.getNome());
        existing.setEndereco(dto.getEndereco());
        existing.setTipoCozinha(dto.getTipoCozinha());
        existing.setHorarioFuncionamento(dto.getHorarioFuncionamento());
        existing.setAtualizadoEm(LocalDateTime.now());
        
        Restaurant updated = restaurantRepository.save(existing);
        return toDTO(updated);
    }
    
    public void delete(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurante", id);
        }
        restaurantRepository.deleteById(id);
    }
    
    private RestaurantDTO toDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .nome(restaurant.getNome())
                .endereco(restaurant.getEndereco())
                .tipoCozinha(restaurant.getTipoCozinha())
                .horarioFuncionamento(restaurant.getHorarioFuncionamento())
                .donoId(restaurant.getDonoId())
                .donoNome(restaurant.getDonoNome())
                .build();
    }
}
