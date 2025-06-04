package com.abn_amro.recipemanagement.domain.entities;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recipe_tags")
@Data
@Builder
public class RecipeTag {
   // RecipeTag handles Many-to-many relation between Recipes and Tags
    @Id
    private Long id;
    @Column("recipe_id")
    private Long recipeId;
    @Column("tag_id")
    private Long tagId;
}
