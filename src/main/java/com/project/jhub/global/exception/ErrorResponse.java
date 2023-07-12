package com.project.jhub.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {

    private int status;
    private String errorMessage;
    private Map<String, String> errorMap;

    @Builder
    public ErrorResponse(int status, String errorMessage, Map<String, String> valid) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.errorMap = valid != null ? valid : new HashMap<>();
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .errorMessage(errorCode.getMessage())
                        .build());
    }

    public void addValidation(String filedName, String errorMessage) {
        this.errorMap.put(filedName, errorMessage);
    }
}
