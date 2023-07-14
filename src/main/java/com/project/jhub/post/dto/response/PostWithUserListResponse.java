package com.project.jhub.post.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PostWithUserListResponse {

    private List<PostWithUserResponse> postWithUserResponseList;

    public PostWithUserListResponse(List<PostWithUserResponse> postWithUserResponseList) {
        this.postWithUserResponseList = postWithUserResponseList;
    }
}
