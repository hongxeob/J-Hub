package com.project.jhub.user.dto.response;

import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String nickname;
    private String introduction;
    private UserRole userRole;

    @Builder
    public UserResponse(Long id, String username, String nickname, String introduction, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.introduction = introduction;
        this.userRole = userRole;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.introduction = user.getIntroduction();
        this.userRole = user.getUserRole();
    }
}