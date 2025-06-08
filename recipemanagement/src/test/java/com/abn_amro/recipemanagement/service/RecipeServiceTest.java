package com.abn_amro.recipemanagement.service;

import com.abn_amro.recipemanagement.constant.ResponseConstant;
import com.abn_amro.recipemanagement.domain.dto.request.IngredientDTO;
import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.dto.request.UserDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import com.abn_amro.recipemanagement.domain.entities.Recipe;
import com.abn_amro.recipemanagement.domain.mapper.IngredientMapper;
import com.abn_amro.recipemanagement.domain.mapper.RecipeMapper;
import com.abn_amro.recipemanagement.event.RecipeRegisteredEvent;
import com.abn_amro.recipemanagement.repository.RecipeRepository;
import com.abn_amro.recipemanagement.serviceImpl.RecipeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientMapper ingredientMapper;
    @Mock
    private RecipeMapper recipeMapper;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private RecipeServiceImpl recipeService;
    @Test
    @DisplayName("Test create recipe service")
    void testCreateRecipe_shouldReturnCreatedRecipe() {
        // Arrange
        RecipeDTO recipeDTO = RecipeDTO.builder().id(1l).title("Test Recipe").build();
        IngredientDTO ingredientDTO = IngredientDTO.builder().id(2l).quantity(2l).name("Tomatoes").build();
        recipeDTO.setIngredients(List.of(ingredientDTO));
        UserDTO mockUser = UserDTO.builder().id(1l).firstName("John").firstName("Does").build();
        Ingredient mappedIngredient = Ingredient.builder().name("Tomato").build();
        Recipe mappedRecipe = Recipe.builder().id(1l).build();
        Recipe savedRecipe = Recipe.builder().id(10l).userId(1l).build();
        Mockito.when(userServiceClient.getUserById(1L))
                .thenReturn(ResponseEntity.ok(ApiResponse.success(mockUser, null, ResponseConstant.STATUS_200, ResponseConstant.MESSAGE_200,false)));
        Mockito.when(recipeMapper.toEntity(recipeDTO)).thenReturn(mappedRecipe);
        Mockito.when(ingredientMapper.toEntityList(recipeDTO.getIngredients())).thenReturn(List.of(mappedIngredient));
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);
        // Act
        Recipe result = recipeService.createRecipe(recipeDTO);
        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(recipeRepository).save(any(Recipe.class));
        verify(eventPublisher).publishEvent(any(RecipeRegisteredEvent.class));
    }
}

