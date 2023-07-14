package com.project.jhub.comment.dto.request;

import com.project.jhub.comment.domain.Comment;
import com.project.jhub.post.domain.Post;
import com.project.jhub.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CommentCreateRequest {

    @NotBlank(message = "댓글을 입력해 주세요.")
    private String content;
    private User user;
    private Post post;
    private String nickname;

    @Builder
    public CommentCreateRequest(String content, String nickname, User user, Post post) {
        this.content = content;
        this.nickname = nickname;
        this.user = user;
        this.post = post;
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .user(user)
                .post(post)
                .build();
    }

    public void insertPostAndAuthor(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
