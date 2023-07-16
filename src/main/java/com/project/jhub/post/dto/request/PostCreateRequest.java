package com.project.jhub.post.dto.request;

import com.project.jhub.post.domain.Category;
import com.project.jhub.post.domain.Post;
import com.project.jhub.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "제목 입력은 필수 입니다.")
    @Size(min = 4, max = 40)
    private String title;

    @NotBlank(message = "내용 입력은 필수 입니다.")
    @Size(min = 4, message = "내용은 4자 이상 작성해 주세요.")
    private String content;

    @NotBlank(message = "작성자를 입력해 주세요.")
    private String username;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

    private User user;

    @Builder
    public PostCreateRequest(String title, String content, String username, Category category, User user) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.category = category;
        this.user = user;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .user(user)
                .build();
    }

    public void insertUser(User user) {
        this.user = user;
    }
}
