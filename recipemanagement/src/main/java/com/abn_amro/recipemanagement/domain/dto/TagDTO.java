package com.abn_amro.recipemanagement.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@Schema(
        name = "Tag",
        description = "Schema to hold RecipeTag information"
)
public class Tag {
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
