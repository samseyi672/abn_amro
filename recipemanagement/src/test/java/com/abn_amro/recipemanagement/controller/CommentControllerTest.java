package com.abn_amro.recipemanagement.controller;


import com.abn_amro.recipemanagement.domain.dto.request.CommentDTO;
import com.abn_amro.recipemanagement.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CommentService commentService;
    private CommentDTO testComment;

    @BeforeEach
    void setUp() {
        testComment = CommentDTO.builder()
                .id(1L)
                .content("Great recipe!")
                .userId(1L)
                .recipeId(10L)
                .build();
    }

    @Test
    @DisplayName("POST /api/v1/comments - should create comment")
    void testCreateComment() throws Exception {
        when(commentService.createComment(any(CommentDTO.class))).thenReturn(testComment);
        mockMvc.perform(post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testComment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.content").value("Great recipe!"));
    }

    @Test
    @DisplayName("GET /api/v1/comments/recipe/{id} - should return comments by recipe")
    void testGetCommentsByRecipe() throws Exception {
        when(commentService.getCommentsByRecipeId(10L)).thenReturn(List.of(testComment));

        mockMvc.perform(get("/api/v1/comments/recipe/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].recipeId").value(10));
    }

    @Test
    @DisplayName("GET /api/v1/comments/user/{id} - should return comments by user")
    void testGetCommentsByUser() throws Exception {
        when(commentService.getCommentsByUserId(1L)).thenReturn(List.of(testComment));
        mockMvc.perform(get("/api/v1/comments/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].userId").value(1));
    }
}

