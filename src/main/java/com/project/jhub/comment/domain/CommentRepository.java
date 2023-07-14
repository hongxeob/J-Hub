package com.project.jhub.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByPostIdAndId(Long postId, Long id);
}
