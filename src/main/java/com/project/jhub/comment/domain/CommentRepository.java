package com.project.jhub.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.post WHERE c.post.id = :postId AND c.id = :commentId")
    Optional<Comment> findByPostIdAndId(@Param("postId") Long postId, @Param("commentId") Long commentId);
}
