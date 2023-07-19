package com.project.jhub.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from User u join u.posts p left join fetch p.comments where u.id = :id order by p.createDate DESC",
            countQuery = "select count(p) from User u join u.posts p where u.id = :id")
    Page<Post> findByUserId(@Param("id") Long userId, Pageable pageable);

    @Query(value = "SELECT distinct p FROM Post p JOIN fetch p.user Left JOIN fetch p.comments ORDER BY p.createDate DESC"
            , countQuery = "SELECT count(p) FROM Post p JOIN  p.user Left JOIN p.comments")
    Page<Post> findAllWithUserAndComments(Pageable pageable);

    @Query("SELECT distinct p FROM Post p JOIN fetch p.user Left JOIN fetch p.comments WHERE p.id = :id")
    Optional<Post> findByIdWithUserAndComments(@Param("id") Long id);

    @Query(value = "select DISTINCT p from Post p join fetch p.user LEFT join fetch p.comments where p.category= :category order by p.createDate DESC"
            , countQuery = "select count(p) from Post p join p.user LEFT join  p.comments where p.category= :category")
    Page<Post> findByCategory(@Param("category") Category category, Pageable pageable);

    @Query(value = "SELECT DISTINCT p FROM Post p JOIN FETCH p.user LEFT JOIN FETCH p.comments WHERE p.title LIKE %:title% ORDER BY p.createDate DESC",
            countQuery = "SELECT COUNT(p) FROM Post p JOIN p.user LEFT JOIN p.comments WHERE p.title LIKE %:title%")
    Page<Post> findByTitle(@Param("title") String title, Pageable pageable);
}