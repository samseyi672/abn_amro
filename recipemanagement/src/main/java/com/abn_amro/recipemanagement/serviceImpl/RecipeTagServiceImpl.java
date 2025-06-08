package com.abn_amro.recipemanagement.serviceImpl;


import com.abn_amro.recipemanagement.domain.dto.request.RecipeTagDTO;
import com.abn_amro.recipemanagement.domain.entities.RecipeTag;
import com.abn_amro.recipemanagement.domain.mapper.RecipeTagMapper;
import com.abn_amro.recipemanagement.repository.RecipeTagRepository;
import com.abn_amro.recipemanagement.service.RecipeTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeTagServiceImpl implements RecipeTagService {

    private final RecipeTagRepository recipeTagRepository;
    private final RecipeTagMapper recipeTagMapper;

    @Override
    public RecipeTagDTO create(RecipeTagDTO dto) {
        RecipeTag saved = recipeTagRepository.save(recipeTagMapper.toEntity(dto));
        return recipeTagMapper.toDto(saved);
    }

    @Override
    public List<RecipeTagDTO> findByRecipeId(Long recipeId) {
        return recipeTagRepository.findByRecipeId(recipeId).stream()
                .map(recipeTagMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RecipeTagDTO> findByTagId(Long tagId) {
        return recipeTagRepository.findByTagId(tagId).stream()
                .map(recipeTagMapper::toDto).collect(Collectors.toList());
    }
}

