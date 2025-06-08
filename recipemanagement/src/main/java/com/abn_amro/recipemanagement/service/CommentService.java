package com.abn_amro.recipemanagement.service;


import com.abn_amro.recipemanagement.domain.dto.request.CommentDTO;
import com.abn_amro.recipemanagement.domain.entities.Comment;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO) ;
    Comment updateComment(Long id,CommentDTO commentDTO);
    List<CommentDTO> getCommentsByUserId(Long userId);
    List<CommentDTO> getCommentsByRecipeId(Long recipeId);
}
