package com.abn_amro.recipemanagement.repository;


import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import com.abn_amro.recipemanagement.domain.entities.Recipe;
import com.abn_amro.recipemanagement.utils.RecipeSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("myunit_test")
@Slf4j
public class RecipeDynamicFilteringTest {
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setup() {
        Recipe potatoSoupRecipe = Recipe.builder().vegetarian(true).servings(2).title("Potato Soup")
                    .userId(1l).instructions("Boil potatoes with salt").build();
        Recipe grilledSalmonRecipe = Recipe.builder().title("Grilled Salmon").userId(1l).vegetarian(false)
                .instructions("Grill salmon in oven")
                .build();
        Ingredient i1 = Ingredient.builder().name("Potato").quantity("3").recipe(potatoSoupRecipe).build();
        Ingredient i2 = Ingredient.builder().name("Salmon").recipe(grilledSalmonRecipe)
                .quantity("2 fillets").build();
        potatoSoupRecipe.setIngredients(List.of(i1));
        grilledSalmonRecipe.setIngredients(List.of(i2));
        recipeRepository.save(potatoSoupRecipe);
        recipeRepository.save(grilledSalmonRecipe);
    }

    @Test
    void shouldFindVegetarianRecipes() {
        Specification<Recipe> spec = RecipeSpecification.hasRecipeVegetarian(true);
        List<Recipe> results = recipeRepository.findAll(spec);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isVegetarian());
    }

    @Test
    void shouldFindRecipesWithServings4() {
        Specification<Recipe> spec = RecipeSpecification.recipeHasServings(4);
        List<Recipe> results = recipeRepository.findAll(spec);
        assertEquals(1, results.size());
        assertEquals(4, results.get(0).getServings());
    }

    @Test
    void shouldFindRecipesByInstructionSearch() {
        Specification<Recipe> spec = RecipeSpecification.recipeContainsInstruction("oven");
        List<Recipe> results = recipeRepository.findAll(spec);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getInstructions().toLowerCase().contains("oven"));
    }

    @Test
    void shouldFindRecipeWithIngredient() {
        Specification<Recipe> spec = RecipeSpecification.recipeHasIngredient("salmon");
        List<Recipe> results = recipeRepository.findAll(spec);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getIngredients().stream().anyMatch(i -> i.getName().equalsIgnoreCase("salmon")));
    }
}
