package com.abn_amro.recipemanagement.serviceImpl;


import com.abn_amro.recipemanagement.domain.dto.request.TagDTO;
import com.abn_amro.recipemanagement.domain.entities.Tag;
import com.abn_amro.recipemanagement.domain.mapper.TagMapper;
import com.abn_amro.recipemanagement.repository.TagRepository;
import com.abn_amro.recipemanagement.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    @Override
    public TagDTO createTag(TagDTO dto) {
        if (tagRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Tag with name already exists");
        }
        Tag saved = tagRepository.save(tagMapper.toEntity(dto));
        return tagMapper.toDto(saved);
    }
    @Override
    public List<TagDTO> getAllTags(int page, int size) {
        Pageable pageable = PageRequest.of(page, size) ;
        return tagRepository.findAll(pageable).map(tagMapper::toDto).stream().toList();
    }
}













































































































