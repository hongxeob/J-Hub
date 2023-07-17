package com.project.jhub.comment.dto.response;

import com.project.jhub.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private String username;
    private String nickname;

    @Builder
    public CommentResponse(Long id, String content, Long postId, LocalDateTime createdAt, String username, String nickname) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.createdAt = createdAt;
        this.username = username;
        this.nickname = nickname;
    }

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.postId = comment.getPost().getId();
        this.nickname = comment.getUser().getNickname();
    }
}
