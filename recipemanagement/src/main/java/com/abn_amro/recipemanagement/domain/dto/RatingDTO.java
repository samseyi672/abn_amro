package com.abn_amro.recipemanagement.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Builder
@Schema(
        name = "Rating",
        description = "Schema to hold Rating information"
)
public class Rating {

    @Schema(
            description = "pass zero if rating is new ", example = "0 or digit"
    )
    private Long id;
    @Schema(
            description = "score", example = "0nly digit"
    )
    @NotNull(message = "score cannot be empty")
    private int score; // range: 1 to 5
    @Schema(
            description = "userId", example = "0nly digit"
    )
    @NotNull(message = "userId cannot be empty")
    private Long userId;
    @Schema(
            description = "recipeId", example = "0nly digit"
    )
    @NotNull(message = "recipeId cannot be empty")
    private Long recipeId;
}






