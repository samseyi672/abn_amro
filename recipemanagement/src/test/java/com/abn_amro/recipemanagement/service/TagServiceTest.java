package com.abn_amro.recipemanagement.service;


import com.abn_amro.recipemanagement.domain.dto.request.TagDTO;
import com.abn_amro.recipemanagement.domain.entities.Tag;
import com.abn_amro.recipemanagement.domain.mapper.TagMapper;
import com.abn_amro.recipemanagement.repository.TagRepository;
import com.abn_amro.recipemanagement.serviceImpl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagMapper tagMapper;
    @InjectMocks
    private TagServiceImpl tagService;
    private Tag tag;
    private TagDTO tagDTO;

    @BeforeEach
    void setUp() {
       // MockitoAnnotations.openMocks(this);
        tag = Tag.builder().id(1L).name("Vegan").build();
        tagDTO = TagDTO.builder().id(1L).name("Vegan").build();
    }

    @Test
    @DisplayName("Test Create Tag - should return DTO")
    void testCreateTag() {
        when(tagRepository.existsByNameIgnoreCase("Vegan")).thenReturn(false);
        when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
        when(tagRepository.save(tag)).thenReturn(tag);
        when(tagMapper.toDto(tag)).thenReturn(tagDTO);
        TagDTO result = tagService.createTag(tagDTO);
        assertNotNull(result);
        assertEquals("Vegan", result.getName());
    }

    @Test
    @DisplayName("Test Get All Tags - should return list")
    void testGetAllTags() {
        when(tagRepository.findAll()).thenReturn(List.of(tag));
        when(tagMapper.toDtoList(List.of(tag))).thenReturn(List.of(tagDTO));
        List<TagDTO> result = tagService.getAllTags(0,10);
        assertEquals(1, result.size());
        assertEquals("Vegan", result.get(0).getName());
    }
}


























































































































