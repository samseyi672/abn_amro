package com.abn_amro.recipemanagement.domain.mapper;

import com.abn_amro.recipemanagement.domain.dto.request.CommentDTO;
import com.abn_amro.recipemanagement.domain.entities.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO toDto(Comment entity);
    Comment toEntity(CommentDTO dto);
}