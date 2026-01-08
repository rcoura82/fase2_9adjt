package com.restaurant.system.application.usecase;

import com.restaurant.system.application.dto.MenuItemDTO;
import com.restaurant.system.application.exception.ResourceNotFoundException;
import com.restaurant.system.domain.model.MenuItem;
import com.restaurant.system.domain.repository.MenuItemRepository;
import com.restaurant.system.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for MenuItem operations
 * Part of Clean Architecture - Application Layer
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemUseCase {
    
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    
    public MenuItemDTO create(MenuItemDTO dto) {
        if (!restaurantRepository.existsById(dto.getRestauranteId())) {
            throw new ResourceNotFoundException("Restaurante", dto.getRestauranteId());
        }
        
        MenuItem menuItem = MenuItem.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .disponivelApenasRestaurante(dto.getDisponivelApenasRestaurante() != null ? 
                        dto.getDisponivelApenasRestaurante() : false)
                .fotoCaminho(dto.getFotoCaminho())
                .restauranteId(dto.getRestauranteId())
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
        
        MenuItem saved = menuItemRepository.save(menuItem);
        return toDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public MenuItemDTO findById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do cardápio", id));
        return toDTO(menuItem);
    }
    
    @Transactional(readOnly = true)
    public List<MenuItemDTO> findAll() {
        return menuItemRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<MenuItemDTO> findByRestauranteId(Long restauranteId) {
        return menuItemRepository.findByRestauranteId(restauranteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public MenuItemDTO update(Long id, MenuItemDTO dto) {
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do cardápio", id));
        
        existing.setNome(dto.getNome());
        existing.setDescricao(dto.getDescricao());
        existing.setPreco(dto.getPreco());
        existing.setDisponivelApenasRestaurante(dto.getDisponivelApenasRestaurante());
        existing.setFotoCaminho(dto.getFotoCaminho());
        existing.setAtualizadoEm(LocalDateTime.now());
        
        MenuItem updated = menuItemRepository.save(existing);
        return toDTO(updated);
    }
    
    public void delete(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item do cardápio", id);
        }
        menuItemRepository.deleteById(id);
    }
    
    private MenuItemDTO toDTO(MenuItem menuItem) {
        return MenuItemDTO.builder()
                .id(menuItem.getId())
                .nome(menuItem.getNome())
                .descricao(menuItem.getDescricao())
                .preco(menuItem.getPreco())
                .disponivelApenasRestaurante(menuItem.getDisponivelApenasRestaurante())
                .fotoCaminho(menuItem.getFotoCaminho())
                .restauranteId(menuItem.getRestauranteId())
                .build();
    }
}
