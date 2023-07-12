package com.project.jhub.user.dto.request;

import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserUpdateRequest {

    @NotBlank(message = "닉네임 입력은 필수 입니다.")
    @Size(min = 2, max = 12)
    private String nickname;

    @NotBlank(message = "정보를 입력해 주세요.")
    @Size(min = 10, max = 150)
    private String introduction;

    @NotNull
    private UserRole userRole;

    @Builder
    public UserUpdateRequest(String nickname, String introduction, UserRole userRole) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.userRole = userRole;
    }

    public UserUpdateRequest(User user) {
        this.nickname = user.getNickname();
        this.introduction = user.getIntroduction();
        this.userRole = user.getUserRole();
    }
}
