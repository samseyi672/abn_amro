package com.abn_amro.recipemanagement.service;

import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.entities.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RecipeService {
    RecipeDTO GetRecipeByRecipeId(Long id);
    Recipe createRecipe(RecipeDTO recipe);
    void deleteById(Long id);
    Page<RecipeDTO> searchRecipeWithDynamicFiltering(Boolean vegetarian, Integer servings, String ingredient, String instructionSearchText, Pageable pageable);
    Page<RecipeDTO> searchRecipeWithDynamicFilteringByUserId(Long id,Boolean vegetarian, Integer servings, String ingredient, String instructionSearchText, Pageable pageable);
}

