package com.abn_amro.recipemanagement.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "Comment",
        description = "Schema to hold Comment information"
)
public class CommentDTO {
    @Schema(
            description = "pass zero to Id if ingredient is new ", example = "0 or corresponding digits"
    )
    private Long id;
    @Schema(
            description = "content", example = "user comment"
    )
    @NotEmpty(message = "content cannot be empty")
    private String content;
    @Schema(
            description = "userId", example = "Only digits"
    )
    @NotNull(message = "content cannot be null")
    private Long userId;
    @Schema(
            description = "recipeId", example = "Only digits"
    )
    @NotNull(message = "recipeId cannot be null")
    private Long recipeId;
}
