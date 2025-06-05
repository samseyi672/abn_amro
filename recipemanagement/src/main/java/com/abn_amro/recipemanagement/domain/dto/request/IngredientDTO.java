package com.abn_amro.recipemanagement.domain.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "Ingredient",
        description = "Schema to hold Ingredient information"
)
public class IngredientDTO {
    @Schema(
            description = "pass zero to Id if ingredient is new ", example = "0 or digits"
    )
    private Long id;
    @Schema(
            description = "name", example = "Seyi/Adam"
    )
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @Schema(
            description = "quantity", example = "Only digit"
    )
    @NotNull(message = "name cannot be empty")
    private Long quantity;
    @Schema(
            description = "recipeId", example = "Only digit"
    )
    @NotNull(message = "recipeId cannot be empty")
    private Long recipeId;
}












