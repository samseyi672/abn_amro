package com.abn_amro.recipemanagement.domain.mapper;

import com.abn_amro.recipemanagement.domain.dto.request.IngredientDTO;
import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import com.abn_amro.recipemanagement.domain.entities.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Mapping(source = "ingredients", target = "ingredients")
    RecipeDTO toDto(Recipe recipe);

    @Mapping(source = "ingredients", target = "ingredients")
    Recipe toEntity(RecipeDTO recipeDTO);

    List<RecipeDTO> toDtoList(List<Recipe> recipes);

    List<Recipe> toEntityList(List<RecipeDTO> recipeDTOs);
}

