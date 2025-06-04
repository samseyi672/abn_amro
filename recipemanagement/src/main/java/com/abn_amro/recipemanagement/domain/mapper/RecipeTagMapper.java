package com.abn_amro.recipemanagement.domain.mapper;


import com.abn_amro.recipemanagement.domain.dto.RecipeTagDTO;
import com.abn_amro.recipemanagement.domain.entities.RecipeTag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeTagMapper {
    RecipeTagDTO toDto(RecipeTag entity);
    RecipeTag toEntity(RecipeTagDTO dto);
}
