package com.abn_amro.recipemanagement.domain.mapper;


import com.abn_amro.recipemanagement.domain.dto.request.RecipeTagDTO;
import com.abn_amro.recipemanagement.domain.entities.RecipeTag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeTagMapper {
    RecipeTagDTO toDto(RecipeTag entity);
    RecipeTag toEntity(RecipeTagDTO dto);
    List<RecipeTagDTO> toDtoList(List<RecipeTag> entities);
}
