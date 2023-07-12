package com.project.jhub.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private final int status;
    private final String message;
    private final T Body;

    @Builder
    public CommonResponse(int status, String message, T body) {
        this.status = status;
        this.message = message;
        Body = body;
    }
}
