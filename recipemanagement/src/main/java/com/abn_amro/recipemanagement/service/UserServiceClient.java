package com.abn_amro.recipemanagement.service;


import com.abn_amro.recipemanagement.domain.dto.request.UserDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.serviceImpl.UserServiceFallback;
import com.abn_amro.recipemanagement.serviceImpl.UserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user",url = "${user.service.url}", fallbackFactory = UserServiceFallbackFactory.class)
public interface UserServiceClient {

    @GetMapping("/api/v1/user/{id}")
    ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable("id") Long id);
}
