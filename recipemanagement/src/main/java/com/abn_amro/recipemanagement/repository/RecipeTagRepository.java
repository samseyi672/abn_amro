package com.abn_amro.recipemanagement.repository;

import com.abn_amro.recipemanagement.domain.entities.RecipeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecipeTagRepository extends JpaRepository<RecipeTag,Long> {
    List<RecipeTag> findByRecipeId(Long recipeId);
}
