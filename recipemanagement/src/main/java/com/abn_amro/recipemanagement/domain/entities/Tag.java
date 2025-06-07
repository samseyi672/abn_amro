package com.abn_amro.recipemanagement.domain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Table(name="tags")
@Entity
@Data
@Builder
public class Tag {
   // Reusable labels like “Vegan” or “Keto”
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}