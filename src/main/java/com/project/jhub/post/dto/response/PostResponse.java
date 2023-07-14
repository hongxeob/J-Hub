package com.project.jhub.post.dto.response;

import com.project.jhub.post.domain.Post;
import com.project.jhub.user.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private UserResponse userResponse;

    @Builder
    public PostResponse(Long id, String title, String content, UserResponse userResponse) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userResponse = userResponse;
    }

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userResponse = post.toDto().getUserResponse();
    }
}
