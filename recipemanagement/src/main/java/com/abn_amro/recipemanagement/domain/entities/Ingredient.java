package com.abn_amro.recipemanagement.domain.entities;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ingredients")
@Builder
@Data
public class Ingredient { //Linked to a specific recipe
    @Id
    private Long id;
    private String name;
    private String quantity;
    @Column("recipe_id")
    private Long recipeId;
}
