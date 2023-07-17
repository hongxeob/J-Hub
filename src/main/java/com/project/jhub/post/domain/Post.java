package com.project.jhub.post.domain;

import com.project.jhub.comment.domain.Comment;
import com.project.jhub.global.domain.BaseEntity;
import com.project.jhub.post.dto.request.PostUpdateRequest;
import com.project.jhub.post.dto.response.PostResponse;
import com.project.jhub.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Builder
    public Post(Long id, String title, String content, User user, List<Comment> comments, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
        this.comments = new ArrayList<>();
    }

    public PostResponse toDto() {
        return PostResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .category(category)
                .createdAt(getCreateDate())
                .userResponse(user.toDto())
                .commentResponseList(comments.stream().map(Comment::toDto).toList())
                .build();
    }

    public void updatePost(PostUpdateRequest updateRequest) {
        this.title = updateRequest.getTitle();
        this.content = updateRequest.getContent();
        this.category = updateRequest.getCategory();
    }
}