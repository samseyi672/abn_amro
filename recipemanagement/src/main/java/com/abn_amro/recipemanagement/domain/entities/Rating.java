package com.abn_amro.recipemanagement.domain.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Table(name = "ratings")
@Entity
@Builder
@Data
public class Rating {
   // 1–5 stars, tied to a user and a recipe
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score; // range: 1 to 5
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "recipe_id")
    private Long recipeId;
}
