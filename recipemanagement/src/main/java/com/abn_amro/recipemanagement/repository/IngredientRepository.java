package com.abn_amro.recipemanagement.repository;


import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
}
