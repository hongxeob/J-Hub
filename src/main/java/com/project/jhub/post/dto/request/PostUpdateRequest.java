package com.project.jhub.post.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PostUpdateRequest {

    @NotBlank(message = "제목 입력은 필수 입니다.")
    @Size(min = 4, max = 40)
    private String title;

    @NotBlank(message = "내용 입력은 필수 입니다.")
    private String content;

    @Builder
    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
