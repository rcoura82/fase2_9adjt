package com.restaurant.system.application.usecase;

import com.restaurant.system.application.dto.MenuItemDTO;
import com.restaurant.system.application.exception.ResourceNotFoundException;
import com.restaurant.system.domain.model.MenuItem;
import com.restaurant.system.domain.repository.MenuItemRepository;
import com.restaurant.system.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemUseCaseTest {
    
    @Mock
    private MenuItemRepository menuItemRepository;
    
    @Mock
    private RestaurantRepository restaurantRepository;
    
    @InjectMocks
    private MenuItemUseCase menuItemUseCase;
    
    private MenuItem menuItem;
    private MenuItemDTO menuItemDTO;
    
    @BeforeEach
    void setUp() {
        menuItem = MenuItem.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .descricao("Traditional Italian pizza")
                .preco(new BigDecimal("25.90"))
                .disponivelApenasRestaurante(false)
                .fotoCaminho("/media/pizza.jpg")
                .restauranteId(1L)
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
        
        menuItemDTO = MenuItemDTO.builder()
                .nome("Pizza Margherita")
                .descricao("Traditional Italian pizza")
                .preco(new BigDecimal("25.90"))
                .disponivelApenasRestaurante(false)
                .fotoCaminho("/media/pizza.jpg")
                .restauranteId(1L)
                .build();
    }
    
    @Test
    void create_ShouldCreateMenuItem_WhenValidData() {
        when(restaurantRepository.existsById(1L)).thenReturn(true);
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);
        
        MenuItemDTO result = menuItemUseCase.create(menuItemDTO);
        
        assertNotNull(result);
        assertEquals("Pizza Margherita", result.getNome());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }
    
    @Test
    void create_ShouldThrowException_WhenRestaurantNotFound() {
        when(restaurantRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> menuItemUseCase.create(menuItemDTO));
        verify(menuItemRepository, never()).save(any(MenuItem.class));
    }
    
    @Test
    void findById_ShouldReturnMenuItem_WhenExists() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        
        MenuItemDTO result = menuItemUseCase.findById(1L);
        
        assertNotNull(result);
        assertEquals("Pizza Margherita", result.getNome());
    }
    
    @Test
    void findById_ShouldThrowException_WhenNotExists() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> menuItemUseCase.findById(1L));
    }
    
    @Test
    void findAll_ShouldReturnAllMenuItems() {
        when(menuItemRepository.findAll()).thenReturn(Arrays.asList(menuItem));
        
        List<MenuItemDTO> result = menuItemUseCase.findAll();
        
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void findByRestauranteId_ShouldReturnMenuItems() {
        when(menuItemRepository.findByRestauranteId(1L)).thenReturn(Arrays.asList(menuItem));
        
        List<MenuItemDTO> result = menuItemUseCase.findByRestauranteId(1L);
        
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void update_ShouldUpdateMenuItem_WhenExists() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);
        
        MenuItemDTO result = menuItemUseCase.update(1L, menuItemDTO);
        
        assertNotNull(result);
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }
    
    @Test
    void delete_ShouldDeleteMenuItem_WhenExists() {
        when(menuItemRepository.existsById(1L)).thenReturn(true);
        
        menuItemUseCase.delete(1L);
        
        verify(menuItemRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void delete_ShouldThrowException_WhenNotExists() {
        when(menuItemRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> menuItemUseCase.delete(1L));
        verify(menuItemRepository, never()).deleteById(1L);
    }
}
