package com.abn_amro.recipemanagement.domain.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "Tag",
        description = "Schema to hold RecipeTag information"
)
public class TagDTO {
    @Schema(
            description = "Id", example = "Only digit"
    )
    @NotNull(message = "recipeId cannot be null")
    private Long id;
    @Schema(
            description = "Id", example = "Only digit"
    )
    @NotNull(message = "recipeId cannot be null")
    private String name;
}
