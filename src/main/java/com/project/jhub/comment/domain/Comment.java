package com.project.jhub.comment.domain;

import com.project.jhub.comment.dto.request.CommentUpdateRequest;
import com.project.jhub.comment.dto.response.CommentResponse;
import com.project.jhub.global.domain.BaseEntity;
import com.project.jhub.post.domain.Post;
import com.project.jhub.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(Long id, String content, Post post, User user) {
        this.id = id;
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public CommentResponse toDto() {
        return CommentResponse.builder()
                .id(id)
                .content(content)
                .nickname(user.getNickname())
                .postId(post.getId())
                .build();
    }

    public void updateComment(CommentUpdateRequest updateRequest) {
        this.content = updateRequest.getContent();
    }
}