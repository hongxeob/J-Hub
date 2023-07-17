package com.project.jhub.post.dto.response;

import com.project.jhub.comment.domain.Comment;
import com.project.jhub.comment.dto.response.CommentResponse;
import com.project.jhub.post.domain.Category;
import com.project.jhub.post.domain.Post;
import com.project.jhub.user.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse userResponse;
    private List<CommentResponse> commentResponseList;

    @Builder
    public PostResponse(Long id, String title, String content, Category category, LocalDateTime createdAt, LocalDateTime updatedAt, UserResponse userResponse, List<CommentResponse> commentResponseList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userResponse = userResponse;
        this.commentResponseList = commentResponseList;
    }

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.createdAt = post.getCreateDate();
        this.updatedAt = post.getUpdateDate();
        this.commentResponseList = post.getComments().stream()
                .map(Comment::toDto)
                .collect(Collectors.toList());
    }
}
