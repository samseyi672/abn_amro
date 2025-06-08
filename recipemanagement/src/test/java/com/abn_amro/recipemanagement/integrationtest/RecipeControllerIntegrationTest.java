package com.abn_amro.recipemanagement.integrationtest;


import com.abn_amro.recipemanagement.domain.dto.request.IngredientDTO;
import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecipeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Test creating a new recipe")
    void testCreateRecipe() {
        IngredientDTO ingredientDTO = IngredientDTO.builder().name("Maggi fresh").quantity(2l).build() ;
        RecipeDTO dto = RecipeDTO.builder().title("Test Recipe").vegetarian(true)
                       .servings(2)
                       .ingredients(List.of(ingredientDTO))
                        .userId(1l)
                        .instructions("Boil and serve").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RecipeDTO> request = new HttpEntity<>(dto, headers);
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity("/api/v1/recipe/", request, ApiResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Test get recipe by recipe id")
    void testGetRecipeById() {
        //create a recipe for test
        RecipeDTO dto = RecipeDTO.builder().userId(1l).title("Test Recipe for Get").servings(4).vegetarian(false).instructions("Grill and serve").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RecipeDTO> request = new HttpEntity<>(dto, headers);
        ResponseEntity<ApiResponse> createResponse = restTemplate.postForEntity("/api/v1/recipe/", request, ApiResponse.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        Long createdId = ((LinkedHashMap<String, Object>) createResponse.getBody().getData()).get("id") != null ?
                Long.valueOf(((LinkedHashMap<String, Object>) createResponse.getBody().getData()).get("id").toString()) : 1L;
        // call get by recipe id
        ResponseEntity<ApiResponse> getResponse = restTemplate.exchange(
                "/api/v1/recipe/" + createdId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
    }

    @Test
    @DisplayName("Test url not found error 404")
    void testGetRecipeByInvalidId() {
        Long invalidId = 999999L;
        ResponseEntity<ApiResponse> getResponse = restTemplate.exchange(
                "/api/v1/recipe/" + invalidId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test dynamic Search of recipes")
    void testSearchRecipes() {
        String url = "/api/v1/recipe/search_recipe?vegetarian=true&servings=2&ingredient=Tomato&instruction=Boil&page=0&size=10";
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}




























































































