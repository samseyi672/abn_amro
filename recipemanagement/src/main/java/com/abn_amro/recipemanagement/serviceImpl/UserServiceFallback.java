package com.abn_amro.recipemanagement.serviceImpl;


import com.abn_amro.recipemanagement.domain.dto.request.UserDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.exception.UserNotFoundException;
import com.abn_amro.recipemanagement.service.UserServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserServiceFallback implements UserServiceClient {
    @Override
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(Long id) {
        log.warn("Fallback activated: User service unavailable.");
       throw new UserNotFoundException("User not found for recipe creation");
    }
}
