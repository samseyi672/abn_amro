package com.abn_amro.recipemanagement.domain.entities;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("comments")
@Builder
@Data
public class Comment {
    @Id
    private Long id;
    private String content;
    @Column("user_id")
    private Long userId;
    @Column("recipe_id")
    private Long recipeId;
}
