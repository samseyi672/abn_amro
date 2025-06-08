package com.abn_amro.recipemanagement.controller;


import com.abn_amro.recipemanagement.constant.ResponseConstant;
import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.entities.Recipe;
import com.abn_amro.recipemanagement.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipeService recipeService;
    @Autowired
    private ObjectMapper objectMapper;
    private RecipeDTO testRecipeDTO;
    private Recipe testRecipe;
    @BeforeEach
    void setUp() {
        testRecipeDTO = RecipeDTO.builder().id(1l).title("Test Recipe").servings(2)
                .vegetarian(true)
                .instructions("Boil and serve").build();
        testRecipe = Recipe.builder().id(1l).title("Test Recipe").servings(2)
                .vegetarian(true)
                .instructions("Boil and serve").build();
    }

    @Test
    @DisplayName("GET /api/v1/recipe/{id} - should return RecipeDTO")
    void testGetRecipeById() throws Exception {
        Mockito.when(recipeService.GetRecipeByRecipeId(eq(1L))).thenReturn(testRecipeDTO);

        mockMvc.perform(get("/api/v1/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(ResponseConstant.STATUS_200))
                .andExpect(jsonPath("$.data.title").value("Test Recipe"));
    }

    @Test
    @DisplayName("POST /api/v1/recipe - should create Recipe")
    void testCreateRecipe() throws Exception {
        Mockito.when(recipeService.createRecipe(any(RecipeDTO.class))).thenReturn(testRecipe);

        mockMvc.perform(post("/api/v1/recipe/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRecipeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Recipe"));
    }

    @Test
    @DisplayName("POST /api/v1/recipe - should fail on validation")
    void testCreateRecipeValidationFailure() throws Exception {
        RecipeDTO invalidRecipe = RecipeDTO.builder().build(); // missing required fields

        mockMvc.perform(post("/api/v1/recipe/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRecipe)))
                .andExpect(status().isBadRequest());
    }


}
