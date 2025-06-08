package com.abn_amro.recipemanagement.serviceImpl;


import com.abn_amro.recipemanagement.domain.dto.request.CommentDTO;
import com.abn_amro.recipemanagement.domain.entities.Comment;
import com.abn_amro.recipemanagement.domain.mapper.CommentMapper;
import com.abn_amro.recipemanagement.exception.CommentNotFoundException;
import com.abn_amro.recipemanagement.repository.CommentRepository;
import com.abn_amro.recipemanagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comment updateComment(Long id,CommentDTO commentDTO) {
        Comment comment  = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setContent(commentDTO.getContent());
        Comment  updatedComment = commentRepository.save(comment);
        return updatedComment;
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment saved = commentRepository.save(commentMapper.toEntity(commentDTO));
        return commentMapper.toDto(saved);
    }

    @Override
    public List<CommentDTO> getCommentsByRecipeId(Long recipeId) {
        return commentRepository.findByRecipeId(recipeId).stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getCommentsByUserId(Long userId) {
       return commentRepository.findByUserId(userId).stream()
                .map(commentMapper::toDto).collect(Collectors.toList());
    }
}









