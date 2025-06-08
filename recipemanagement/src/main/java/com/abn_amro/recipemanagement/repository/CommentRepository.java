package com.abn_amro.recipemanagement.repository;

import com.abn_amro.recipemanagement.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
