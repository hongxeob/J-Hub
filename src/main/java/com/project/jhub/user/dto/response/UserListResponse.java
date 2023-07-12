package com.project.jhub.user.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UserListResponse {

    private List<UserResponse> userResponseList;

    public UserListResponse(List<UserResponse> userResponseList) {
        this.userResponseList = userResponseList;
    }
}
