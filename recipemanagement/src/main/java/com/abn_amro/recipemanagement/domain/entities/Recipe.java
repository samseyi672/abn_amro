package com.abn_amro.recipemanagement.domain.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Table(name = "recipes")
@Entity
@Builder
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean vegetarian;
    private int servings;
    private String instructions;
    @Column(name = "user_id")
    private Long userId;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingredient> ingredients;
}





























