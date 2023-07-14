package com.project.jhub.comment.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CommentUpdateRequest {

    @NotBlank(message = "댓글을 입력해 주세요.")
    private String content;

    public CommentUpdateRequest(String content) {
        this.content = content;
    }
}
