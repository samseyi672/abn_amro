package com.abn_amro.recipemanagement.repository;

import com.abn_amro.recipemanagement.domain.entities.RecipeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeTagRepository extends JpaRepository<RecipeTag,Long> {
}
