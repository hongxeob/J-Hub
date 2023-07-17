package com.project.jhub.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    @Query("SELECT distinct p FROM Post p JOIN FETCH p.user Left JOIN FETCH p.comments ORDER BY p.createDate DESC")
    List<Post> findAllWithUserAndComments();

    @Query("SELECT distinct p FROM Post p JOIN FETCH p.user Left JOIN FETCH p.comments WHERE p.id = :id")
    Optional<Post> findByIdWithUserAndComments(@Param("id") Long id);

    List<Post> findByCategory(Category category);

}
