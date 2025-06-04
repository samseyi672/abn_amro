package com.abn_amro.recipemanagement.domain.entities;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tags")
@Data
@Builder
public class Tag {
   // Reusable labels like “Vegan” or “Keto”
    @Id
    private Long id;
    private String name;
}