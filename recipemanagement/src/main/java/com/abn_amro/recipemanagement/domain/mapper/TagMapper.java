package com.abn_amro.recipemanagement.domain.mapper;


import com.abn_amro.recipemanagement.domain.dto.request.TagDTO;
import com.abn_amro.recipemanagement.domain.entities.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDto(Tag entity);
    Tag toEntity(TagDTO dto);
}
