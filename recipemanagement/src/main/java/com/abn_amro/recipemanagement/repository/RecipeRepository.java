package com.abn_amro.recipemanagement.repository;


import com.abn_amro.recipemanagement.domain.entities.Recipe;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends ReactiveCrudRepository<Recipe, Long> {

}
