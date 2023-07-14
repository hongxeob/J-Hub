package com.project.jhub.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //공용
    INVALID_INPUT_VALUE(400, "잘못된 값을 입력 하셨습니다."),

    // 유저
    DUPLICATED_USERNAME(400, "중복된 ID입니다."),
    DUPLICATED_NICKNAME(400, "이미 사용중인 닉네임 입니다."),
    NOT_FOUND_USER(404, "회원이 존재하지 않습니다."),

    // 게시물
    NOT_FOUND_POST(404, "게시물이 존재하지 않습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
