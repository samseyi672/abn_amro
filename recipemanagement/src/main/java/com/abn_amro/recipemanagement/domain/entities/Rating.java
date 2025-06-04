package com.abn_amro.recipemanagement.domain.entities;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ratings")
@Builder
@Data
public class Rating {
   // 1–5 stars, tied to a user and a recipe
    @Id
    private Long id;
    private int score; // range: 1 to 5
    @Column("user_id")
    private Long userId;
    @Column("recipe_id")
    private Long recipeId;
}
