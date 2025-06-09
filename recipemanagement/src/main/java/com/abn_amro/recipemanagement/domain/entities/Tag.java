package com.abn_amro.recipemanagement.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name="tags")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
   // Reusable labels like “Vegan” or “Keto”
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}