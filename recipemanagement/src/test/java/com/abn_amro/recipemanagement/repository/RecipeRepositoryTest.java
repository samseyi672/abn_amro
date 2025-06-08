package com.abn_amro.recipemanagement.repository;


import com.abn_amro.recipemanagement.domain.entities.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class RecipeRepositoryTest {

    @Autowired
    RecipeRepository recipeRepository ;

    @Test
    void firstTest(){
        assertNotNull(recipeRepository);
    }

    @Test
    @Sql("/testRecipe.sql")
    void testData(){

    }

    @Test
    @DisplayName("Should count recipes by userId")
    void testCountOfRecipeByUserId() {
        Recipe riceRecipe = Recipe.builder()
                      .title("Rice")
                      .userId(1l)
                       .servings(2)
                       .instructions("Boil the rice at for at least 15mins")
                       .vegetarian(true)
                     .build();
        Recipe beanRecipe = Recipe.builder()
                .title("Beans")
                .userId(1l)
                .servings(4)
                .instructions("Cook")
                .vegetarian(true)
                .build();
        Recipe meatRecipe = Recipe.builder()
                .title("Meat")
                .userId(2l)
                .servings(3)
                .instructions("Grill at least 15mins")
                .vegetarian(true)
                .build();
        recipeRepository.saveAll(List.of(riceRecipe,beanRecipe,meatRecipe));
        long count = recipeRepository.countRecipeByUserId(1l) ;
        assertEquals(2, count);
    }



}


















