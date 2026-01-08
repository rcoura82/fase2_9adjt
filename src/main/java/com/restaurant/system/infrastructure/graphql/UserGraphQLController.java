package com.restaurant.system.infrastructure.graphql;

import com.restaurant.system.application.dto.UserDTO;
import com.restaurant.system.application.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * GraphQL Controller for User queries
 * Part of Clean Architecture - Infrastructure Layer
 */
@Controller
@RequiredArgsConstructor
public class UserGraphQLController {
    
    private final UserUseCase userUseCase;
    
    @QueryMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    public UserDTO user(@Argument Long id) {
        return userUseCase.findById(id);
    }
    
    @QueryMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    public List<UserDTO> users() {
        return userUseCase.findAll();
    }
}
