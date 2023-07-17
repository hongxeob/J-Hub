package com.project.jhub.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from User u join u.posts p left join fetch p.comments where u.id = :id order by p.createDate DESC")
    List<Post> findByUserId(@Param("id") Long userId);

    @Query("SELECT distinct p FROM Post p JOIN FETCH p.user Left JOIN FETCH p.comments ORDER BY p.createDate DESC")
    List<Post> findAllWithUserAndComments();

    @Query("SELECT distinct p FROM Post p JOIN FETCH p.user Left JOIN FETCH p.comments WHERE p.id = :id")
    Optional<Post> findByIdWithUserAndComments(@Param("id") Long id);

    @Query("select DISTINCT p from Post p join fetch p.user LEFT join fetch p.comments where p.category= :category")
    List<Post> findByCategory(@Param("category") Category category);
}