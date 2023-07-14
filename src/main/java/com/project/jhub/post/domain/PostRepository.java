package com.project.jhub.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    @Query("select p from Post p join fetch p.user")
    List<Post> findAllWithUser();

    @Query("select p from Post p JOIN fetch p.user where p.id= :id")
    Optional<Post> findByIdWithUser(@Param("id") Long id);
}
