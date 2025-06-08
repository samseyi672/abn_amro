package com.abn_amro.recipemanagement.utils;


import com.abn_amro.recipemanagement.domain.entities.Recipe;
import com.abn_amro.recipemanagement.domain.enums.RecipeFilterEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;


/**
 * logger.
 * <p>
 * this utility class handles dynamic filtering for all filters
 * such as ingredients,servings,instructions,vegetarians optionally
 * </p>
 * @author Sam
 * @since 2025
 */
public class RecipeSpecification {

    public static Specification<Recipe> hasRecipeVegetarian(Boolean vegetarian) {
        return (root, query, cb) ->
                vegetarian == null ? null : cb.equal(root.get(RecipeFilterEnum.VEGETARIAN.getValue()), vegetarian);
    }

    public static Specification<Recipe> recipeHasServings(Integer servings) {
        return (root, query, cb) ->
                servings == null ? null : cb.equal(root.get(RecipeFilterEnum.SERVINGS.getValue()), servings);
    }

    public static Specification<Recipe> recipeHasIngredient(String ingredientName) {
        return (root, query, cb) -> {
            if (ingredientName == null || ingredientName.isBlank()) return null;
            Join<Object, Object> ingredients = root.join(RecipeFilterEnum.INGREDIENTS.getValue(), JoinType.LEFT);
            return cb.like(cb.lower(ingredients.get("name")), "%" + ingredientName.toLowerCase() + "%");
        };
    }

    public static Specification<Recipe> recipeContainsInstruction(String instructionSearchText) {
        return (root, query, cb) -> {
            if (instructionSearchText == null || instructionSearchText.isBlank()) return null;
            return cb.like(cb.lower(root.get(RecipeFilterEnum.INSTRUCTIONS.getValue())), "%" + instructionSearchText.toLowerCase() + "%");
        };
    }
}









