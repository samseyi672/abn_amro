package com.abn_amro.recipemanagement.domain.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Table(name = "comments")
@Entity
@Builder
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "recipe_id")
    private Long recipeId;
}
