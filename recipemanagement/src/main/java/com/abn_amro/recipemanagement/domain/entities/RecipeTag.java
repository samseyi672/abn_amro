package com.abn_amro.recipemanagement.domain.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;


@Table(name = "recipe_tags")
@Entity
@Data
@Builder
public class RecipeTag {
   // RecipeTag handles Many-to-many relation between Recipes and Tags
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "recipe_id")
    private Long recipeId;
    @Column(name="tag_id")
    private Long tagId;
}
