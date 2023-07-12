package com.project.jhub.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 유저
    DUPLICATED_USERNAME(400, "중복된 ID입니다."),
    DUPLICATED_NICKNAME(400, "이미 사용중인 닉네임 입니다."),
    NOT_FOUND_USER(404, "회원이 존재하지 않습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
