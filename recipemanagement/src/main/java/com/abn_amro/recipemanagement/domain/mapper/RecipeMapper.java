package com.abn_amro.recipemanagement.domain.mapper;

import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.entities.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);
    RecipeDTO toDto(Recipe recipe);
    Recipe toEntity(RecipeDTO dto);
    List<RecipeDTO> toDtoList(List<Recipe> entities);
}
