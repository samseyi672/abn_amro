package com.abn_amro.recipemanagement.service;

import com.abn_amro.recipemanagement.domain.dto.request.TagDTO;
import java.util.List;

public interface TagService {
    TagDTO createTag(TagDTO dto);
    List<TagDTO> getAllTags(int page , int size);
}
