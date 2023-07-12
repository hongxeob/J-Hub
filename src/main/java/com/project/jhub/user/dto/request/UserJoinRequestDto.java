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
    @Size(min = 4, max = 16, message = "ID는 4~16 글자 사이만 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "영어만 가능합니다.")
    private String username;

    @NotBlank(message = "닉네임 입력은 필수 입니다.")
    @Size(min = 2, max = 12, message = "닉네임은 2~12글자 사이만 가능힙니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "특수문자를 제외한 문자로 지정해주세요.")
    private String nickname;

    @NotBlank(message = "정보를 입력해 주세요.")
    @Size(min = 10, max = 150)
    private String introduction;

    private UserRole userRole;

    @Builder
    public UserJoinRequestDto(String username, String nickname, String introduction, UserRole userRole) {
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
