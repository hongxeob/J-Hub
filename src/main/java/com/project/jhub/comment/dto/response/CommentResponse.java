package com.project.jhub.comment.dto.response;

import com.project.jhub.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;

    @Builder
    public CommentResponse(Long id, String content, Long postId, String nickname) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.nickname = nickname;
    }

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.postId = comment.getPost().getId();
        this.nickname = comment.getUser().getNickname();
    }
}
