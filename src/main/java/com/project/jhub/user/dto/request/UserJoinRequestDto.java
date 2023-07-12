package com.project.jhub.user.dto.request;

import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserJoinRequestDto {

    @NotBlank(message = "아이디 입력은 필수 입니다.")
    @Size(min = 4, max = 12)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$")
    private String username;

    @NotBlank(message = "닉네임 입력은 필수 입니다.")
    @Size(min = 2, max = 12)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$")
    private String nickname;

    @NotBlank(message = "정보를 입력해 주세요.")
    @Size(min = 10, max = 150)
    private String introduction;

    private UserRole userRole;

    @Builder
    public UserJoinRequestDto(String username, String nickname, String introduction,UserRole userRole) {
        this.username = username;
        this.nickname = nickname;
        this.introduction = introduction;
        this.userRole = userRole;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .introduction(introduction)
                .userRole(userRole)
                .build();
    }
}
