package com.abn_amro.recipemanagement.domain.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Table(name="ingredients")
@Entity
@Builder
@Data
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
