package com.restaurant.system.application.usecase;

import com.restaurant.system.application.dto.RestaurantDTO;
import com.restaurant.system.application.exception.ResourceNotFoundException;
import com.restaurant.system.domain.model.Restaurant;
import com.restaurant.system.domain.model.User;
import com.restaurant.system.domain.repository.RestaurantRepository;
import com.restaurant.system.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {
    
    @Mock
    private RestaurantRepository restaurantRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private RestaurantUseCase restaurantUseCase;
    
    private Restaurant restaurant;
    private RestaurantDTO restaurantDTO;
    private User user;
    
    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();
        
        restaurant = Restaurant.builder()
                .id(1L)
                .nome("Test Restaurant")
                .endereco("Test Address")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h-22h")
                .donoId(1L)
                .donoNome("testuser")
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
        
        restaurantDTO = RestaurantDTO.builder()
                .nome("Test Restaurant")
                .endereco("Test Address")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h-22h")
                .donoId(1L)
                .build();
    }
    
    @Test
    void create_ShouldCreateRestaurant_WhenValidData() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
        
        RestaurantDTO result = restaurantUseCase.create(restaurantDTO);
        
        assertNotNull(result);
        assertEquals("Test Restaurant", result.getNome());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }
    
    @Test
    void create_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> restaurantUseCase.create(restaurantDTO));
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }
    
    @Test
    void findById_ShouldReturnRestaurant_WhenExists() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        
        RestaurantDTO result = restaurantUseCase.findById(1L);
        
        assertNotNull(result);
        assertEquals("Test Restaurant", result.getNome());
    }
    
    @Test
    void findById_ShouldThrowException_WhenNotExists() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> restaurantUseCase.findById(1L));
    }
    
    @Test
    void findAll_ShouldReturnAllRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant));
        
        List<RestaurantDTO> result = restaurantUseCase.findAll();
        
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void update_ShouldUpdateRestaurant_WhenExists() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
        
        RestaurantDTO result = restaurantUseCase.update(1L, restaurantDTO);
        
        assertNotNull(result);
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }
    
    @Test
    void delete_ShouldDeleteRestaurant_WhenExists() {
        when(restaurantRepository.existsById(1L)).thenReturn(true);
        
        restaurantUseCase.delete(1L);
        
        verify(restaurantRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void delete_ShouldThrowException_WhenNotExists() {
        when(restaurantRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> restaurantUseCase.delete(1L));
        verify(restaurantRepository, never()).deleteById(1L);
    }
}
