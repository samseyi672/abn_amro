package com.abn_amro.recipemanagement.repository;

import com.abn_amro.recipemanagement.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    boolean existsByNameIgnoreCase(String name);
}
