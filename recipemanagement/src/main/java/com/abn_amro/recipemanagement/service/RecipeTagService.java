package com.abn_amro.recipemanagement.service;

import com.abn_amro.recipemanagement.domain.dto.request.RecipeTagDTO;

import java.util.List;

public interface RecipeTagService {
    RecipeTagDTO create(RecipeTagDTO dto);
    List<RecipeTagDTO> findByRecipeId(Long recipeId);
    List<RecipeTagDTO> findByTagId(Long tagId);
}
