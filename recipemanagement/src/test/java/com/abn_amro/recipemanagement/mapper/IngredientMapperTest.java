package com.abn_amro.recipemanagement.mapper;

import com.abn_amro.recipemanagement.domain.dto.request.IngredientDTO;
import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import com.abn_amro.recipemanagement.domain.mapper.IngredientMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientMapperTest {

    private final IngredientMapper mapper = Mappers.getMapper(IngredientMapper.class);

    @Test
    @DisplayName("Test to map ingredient to ingredientdto")
    void shouldMapIngredientToDto() {
        Ingredient ingredient = Ingredient.builder().id(1l).quantity("1 tsp").name("Salt").build();
        IngredientDTO dto = mapper.toDto(ingredient);
        assertNotNull(dto);
        assertEquals(ingredient.getId(), dto.getId());
        assertEquals(ingredient.getName(), dto.getName());
        assertEquals(ingredient.getQuantity(), dto.getQuantity());
    }

    @Test
    @DisplayName("Test to map ingredientdto to ingredient")
    void shouldMapDtoToIngredient() {
        IngredientDTO dto = IngredientDTO.builder().id(2l).quantity(2l).name("Sugar").build();
        Ingredient ingredient = mapper.toEntity(dto);
        assertNotNull(ingredient);
        assertEquals(dto.getId(), ingredient.getId());
        assertEquals(dto.getName(), ingredient.getName());
        assertEquals(dto.getQuantity(), ingredient.getQuantity());
    }

    @Test
    @DisplayName("Test to map ingredientlist to ingredientdtolist")
    void shouldMapListToDtoList() {
        Ingredient ing1 = Ingredient.builder().id(1l).quantity("3 cups").name("Flour").build();
        Ingredient ing2 = Ingredient.builder().id(2l).name("Butter").quantity("100g").build();
        List<IngredientDTO> dtos = mapper.toDtoList(List.of(ing1, ing2));
        assertEquals(2, dtos.size());
        assertEquals("Flour", dtos.get(0).getName());
        assertEquals("Butter", dtos.get(1).getName());
    }

    @Test
    @DisplayName("Test to map ingredientdtolist to ingredientlist")
    void shouldMapDtoListToEntityList() {
        IngredientDTO dto1 = IngredientDTO.builder().name("Milk").id(1l).quantity(1l).build();
        IngredientDTO dto2 = IngredientDTO.builder().id(2l).name("Eggs").quantity(2l).build();
        List<Ingredient> ingredients = mapper.toEntityList(List.of(dto1, dto2));
        assertEquals(2, ingredients.size());
        assertEquals("Milk", ingredients.get(0).getName());
        assertEquals("Eggs", ingredients.get(1).getName());
    }
}




