package com.abn_amro.recipemanagement.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="ingredients")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient { //Linked to a specific recipe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String quantity;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
