package com.abn_amro.recipemanagement.domain.mapper;

import com.abn_amro.recipemanagement.domain.dto.request.RatingDTO;
import com.abn_amro.recipemanagement.domain.entities.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingDTO toDto(Rating entity);
    Rating toEntity(RatingDTO dto);
}