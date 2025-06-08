package com.abn_amro.recipemanagement.domain.mapper;


import com.abn_amro.recipemanagement.domain.dto.request.TagDTO;
import com.abn_amro.recipemanagement.domain.entities.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDto(Tag entity);
    Tag toEntity(TagDTO dto);
    List<TagDTO> toDtoList(List<Tag> tags);
    List<Tag> toEntityList(List<TagDTO> tagDTOs);
}
