package com.abn_amro.recipemanagement.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Schema(
        name = "RecipeTag",
        description = "Schema to hold RecipeTag information"
)
public class RecipeTagDTO {
    @Schema(
            description = "pass zero if recipe is new ", example = "Only digit"
    )
    private Long id;
    @Schema(
            description = "recipeId", example = "Only digit"
    )
    @NotNull(message = "recipeId cannot be null")
    private Long recipeId;
    @Schema(
            description = "TagId", example = "Only digit"
    )
    @NotNull(message = "tagId cannot be null")
    private Long tagId;
}



