package com.abn_amro.recipemanagement.domain.mapper;


import com.abn_amro.recipemanagement.domain.dto.request.IngredientDTO;
import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    @Mapping(source = "recipe.id", target = "recipeId")
    IngredientDTO toDto(Ingredient ingredient);
    @Mapping(source = "recipeId", target = "recipe.id")
    Ingredient toEntity(IngredientDTO dto);
    List<IngredientDTO> toDtoList(List<Ingredient> ingredients);
    List<Ingredient> toEntityList(List<IngredientDTO> dtos);
}
