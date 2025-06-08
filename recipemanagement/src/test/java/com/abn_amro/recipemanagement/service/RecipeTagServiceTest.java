package com.abn_amro.recipemanagement.service;


import com.abn_amro.recipemanagement.domain.dto.request.RecipeTagDTO;
import com.abn_amro.recipemanagement.domain.entities.RecipeTag;
import com.abn_amro.recipemanagement.domain.mapper.RecipeTagMapper;
import com.abn_amro.recipemanagement.repository.RecipeTagRepository;
import com.abn_amro.recipemanagement.serviceImpl.RecipeTagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeTagServiceTest {
    @Mock
    private RecipeTagRepository recipeTagRepository;
    @Mock
    private RecipeTagMapper recipeTagMapper;
    @InjectMocks
    private RecipeTagServiceImpl recipeTagService;
    private RecipeTag recipeTag;
    private RecipeTagDTO recipeTagDTO;

    @BeforeEach
    void setUp() {
       // MockitoAnnotations.openMocks(this);
        recipeTag = RecipeTag.builder()
                .id(1L)
                .recipeId(100L)
                .tagId(200L)
                .build();
        recipeTagDTO = RecipeTagDTO.builder()
                .id(1L)
                .recipeId(100L)
                .tagId(200L)
                .build();
    }

    @Test
    @DisplayName("Testing Create RecipeTag")
    void testCreateRecipeTag() {
        when(recipeTagMapper.toEntity(recipeTagDTO)).thenReturn(recipeTag);
        when(recipeTagRepository.save(recipeTag)).thenReturn(recipeTag);
        when(recipeTagMapper.toDto(recipeTag)).thenReturn(recipeTagDTO);
        RecipeTagDTO saved = recipeTagService.create(recipeTagDTO);
        assertNotNull(saved);
        assertEquals(100L, saved.getRecipeId());
        verify(recipeTagRepository, times(1)).save(recipeTag);
    }

    @Test
    @DisplayName("Testing Find by Recipe ID-This should return list")
    void testFindByRecipeId() {
        when(recipeTagRepository.findByRecipeId(100L)).thenReturn(List.of(recipeTag));
        when(recipeTagMapper.toDtoList(List.of(recipeTag))).thenReturn(List.of(recipeTagDTO));
        List<RecipeTagDTO> result = recipeTagService.findByRecipeId(100L);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(recipeTagRepository, times(1)).findByRecipeId(100L);
    }

    @Test
    @DisplayName("Find by Tag ID - should return list")
    void testFindByTagId() {
        when(recipeTagRepository.findByTagId(200L)).thenReturn(List.of(recipeTag));
        when(recipeTagMapper.toDtoList(List.of(recipeTag))).thenReturn(List.of(recipeTagDTO));

        List<RecipeTagDTO> result = recipeTagService.findByTagId(200L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(recipeTagRepository, times(1)).findByTagId(200L);
    }
}
