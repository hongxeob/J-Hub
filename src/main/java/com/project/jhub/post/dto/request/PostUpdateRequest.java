package com.project.jhub.post.dto.request;

import com.project.jhub.post.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PostUpdateRequest {

    @NotBlank(message = "제목 입력은 필수 입니다.")
    @Size(min = 4, max = 40)
    private String title;

    @NotBlank(message = "내용 입력은 필수 입니다.")
    private String content;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

    @NotNull(message = "본인 ID를 입력해주세요.")
    private String username;

    @Builder
    public PostUpdateRequest(String title, String content, String username, Category category) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.category = category;
    }
}
