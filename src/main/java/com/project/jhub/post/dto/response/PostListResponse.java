package com.project.jhub.post.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PostListResponse {

    private List<PostResponse> postResponseList;

    public PostListResponse(List<PostResponse> postResponseList) {
        this.postResponseList = postResponseList;
    }
}
