package com.abn_amro.recipemanagement.domain.dto.request;


import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(
        name = "Recipe",
        description = "Schema to hold Recipe information"
)
public class RecipeDTO {
    @Schema(
            description = "pass zero if recipe is new ", example = "0 or recipe id"
    )
    private Long id;

    @NotEmpty(message = "title cannot be empty")
    @Schema(
            description = "Title", example = "Mr/Mrs/Dr/Prof"
    )
    private String title;
    @Schema(
            description = "Vegetarian", example = "vegetarian"
    )
    private boolean vegetarian;
    @Min(value = 1, message = "Servings must be at least 1")
    @Schema(
            description = "Servings", example = "1/2/3/4"
    )
    private int servings;
    @NotEmpty(message = "Instructions cannot be empty")
    @Schema(
            description = "instructions", example = "add oil and pepper smint"
    )
    private String instructions;
    @NotNull(message = "User ID is required")
    @Schema(
            description = "user id", example = "id of the user"
    )
    private Long userId;
    private List<IngredientDTO> ingredients;
}











































































