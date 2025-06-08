package com.abn_amro.recipemanagement.mapper;




import com.abn_amro.recipemanagement.domain.dto.request.IngredientDTO;
import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import com.abn_amro.recipemanagement.domain.entities.Recipe;
import com.abn_amro.recipemanagement.domain.mapper.RecipeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeMapperTest {
    private final RecipeMapper recipeMapper = Mappers.getMapper(RecipeMapper.class);

    @Test
    @DisplayName("Test to map recipe to recipedto")
    void shouldMapRecipeToRecipeDTO() {
        Ingredient ingredient = Ingredient.builder().id(1l).name("Potato").quantity("2 pieces").build();
        Recipe recipe = Recipe.builder().id(100l).title("Mashed Potatoes").servings(4).vegetarian(true).instructions("Boil and mash the potatoes.").userId(1l).ingredients(List.of(ingredient)).build();
        RecipeDTO dto = recipeMapper.toDto(recipe);
        assertNotNull(dto);
        assertEquals(recipe.getId(), dto.getId());
        assertEquals(recipe.getTitle(), dto.getTitle());
        assertEquals(recipe.getUserId(), dto.getUserId());
        assertEquals(recipe.getServings(), dto.getServings());
        assertEquals(recipe.isVegetarian(), dto.isVegetarian());
        assertEquals(recipe.getInstructions(), dto.getInstructions());
        assertNotNull(dto.getIngredients());
        assertEquals(1, dto.getIngredients().size());
        assertEquals("Potato", dto.getIngredients().get(0).getName());
    }

    @Test
    @DisplayName("Test to map recipedto to recipe")
    void shouldMapRecipeDTOToRecipe() {
        IngredientDTO ingredientDTO = IngredientDTO.builder().id(2l).name("Carrot").quantity(3l).build();
        RecipeDTO dto = RecipeDTO.builder().id(10l).ingredients(List.of(ingredientDTO))
                        .instructions("Boil the carrots and blend.").servings(3).vegetarian(true)
                        .build();
        Recipe recipe = recipeMapper.toEntity(dto);
        assertNotNull(recipe);
        assertEquals(dto.getId(), recipe.getId());
        assertEquals(dto.getTitle(), recipe.getTitle());
        assertEquals(dto.getUserId(), recipe.getUserId());
        assertEquals(dto.getServings(), recipe.getServings());
        assertEquals(dto.isVegetarian(), recipe.isVegetarian());
        assertEquals(dto.getInstructions(), recipe.getInstructions());
        assertNotNull(recipe.getIngredients());
        assertEquals(1, recipe.getIngredients().size());
        assertEquals("Carrot", recipe.getIngredients().get(0).getName());
    }
}















































