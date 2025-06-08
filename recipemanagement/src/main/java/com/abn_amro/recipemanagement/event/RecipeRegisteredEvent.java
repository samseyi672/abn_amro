package com.abn_amro.recipemanagement.event;


import com.abn_amro.recipemanagement.domain.dto.request.IngredientDTO;
import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecipeRegisteredEvent {
    private String lastName;
    private String firstName;
    private String userName;
    private String title;
    private boolean vegetarian;
    private int servings;
    private String instructions;
    private List<IngredientDTO> ingredients;
}
