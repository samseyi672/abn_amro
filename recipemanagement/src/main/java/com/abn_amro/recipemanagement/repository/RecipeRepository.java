package com.abn_amro.recipemanagement.repository;


import com.abn_amro.recipemanagement.domain.entities.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
    @Query("SELECT COUNT(r) FROM Recipe r WHERE r.userId = :userId")
    long countRecipeByUserId(@Param("userId") Long userId);
    @Query("SELECT * FROM Recipe r WHERE r.userId = :userId")
    Page<Recipe> selectRecipeByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("""
    SELECT r FROM Recipe r 
    WHERE r.userId = :userId 
      AND (:vegetarian IS NULL OR r.vegetarian = :vegetarian) 
      AND (:servings IS NULL OR r.servings = :servings)
      """)
    Page<Recipe> findRecipesByUserFilters(
            @Param("userId") Long userId,
            @Param("vegetarian") Boolean vegetarian,
            @Param("servings") Integer servings,
            Pageable pageable
    );
    List<Recipe> findByVegetarian(boolean vegetarian);
    List<Recipe> findByServings(int servings);
    List<Recipe> findByVegetarianAndServings(boolean vegetarian, int servings);

}
