package com.abn_amro.recipemanagement.domain.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recipes")
@Builder
@Data
public class Recipe { //Created by a user,with instructions
    @Id
    private Long id;
    private String title;
    private boolean vegetarian;
    private int servings;
    private String instructions;
    @Column("user_id")
    private Long userId;
}
