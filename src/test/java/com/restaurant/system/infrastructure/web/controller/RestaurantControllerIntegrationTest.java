package com.restaurant.system.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.system.application.dto.RestaurantDTO;
import com.restaurant.system.application.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "testuser", roles = {"DOCTOR"})
class RestaurantControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private UserDTO userDTO;
    private RestaurantDTO restaurantDTO;
    private Long userId;
    
    @BeforeEach
    void setUp() throws Exception {
        // Create a user first
        userDTO = UserDTO.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .role(com.restaurant.system.domain.model.UserRole.DOCTOR)
                .fullName("Test User")
                .build();
        
        String userResponse = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        
        UserDTO createdUser = objectMapper.readValue(userResponse, UserDTO.class);
        userId = createdUser.getId();
        
        // Prepare restaurant DTO
        restaurantDTO = RestaurantDTO.builder()
                .nome("Test Restaurant")
                .endereco("123 Test Street")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h-22h")
                .donoId(userId)
                .build();
    }
    
    @Test
    void createRestaurant_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Test Restaurant"))
                .andExpect(jsonPath("$.tipoCozinha").value("Italiana"));
    }
    
    @Test
    void getRestaurant_ShouldReturnRestaurant() throws Exception {
        // Create restaurant
        String response = mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        
        RestaurantDTO created = objectMapper.readValue(response, RestaurantDTO.class);
        
        // Get restaurant
        mockMvc.perform(get("/api/restaurants/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Test Restaurant"));
    }
    
    @Test
    void getAllRestaurants_ShouldReturnList() throws Exception {
        // Create restaurant
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated());
        
        // Get all
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Test Restaurant"));
    }
    
    @Test
    void updateRestaurant_ShouldReturnUpdated() throws Exception {
        // Create restaurant
        String response = mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        
        RestaurantDTO created = objectMapper.readValue(response, RestaurantDTO.class);
        created.setNome("Updated Restaurant");
        
        // Update
        mockMvc.perform(put("/api/restaurants/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Updated Restaurant"));
    }
    
    @Test
    void deleteRestaurant_ShouldReturnNoContent() throws Exception {
        // Create restaurant
        String response = mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        
        RestaurantDTO created = objectMapper.readValue(response, RestaurantDTO.class);
        
        // Delete
        mockMvc.perform(delete("/api/restaurants/" + created.getId()))
                .andExpect(status().isNoContent());
        
        // Verify deleted
        mockMvc.perform(get("/api/restaurants/" + created.getId()))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void createRestaurant_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
        RestaurantDTO invalid = RestaurantDTO.builder()
                .nome("") // Invalid: empty name
                .build();
        
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}
