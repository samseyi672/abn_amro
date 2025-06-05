package com.abn_amro.recipemanagement.service;

import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface RecipeService {
    Mono<RecipeDTO> findById(Long id);
    Flux<RecipeDTO> findAll();
    Mono<ResponseEntity<ApiResponse<String>>> save(RecipeDTO recipe);
    Mono<Void> deleteById(Long id);
}

