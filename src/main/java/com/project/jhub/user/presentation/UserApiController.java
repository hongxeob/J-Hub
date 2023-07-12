package com.project.jhub.user.presentation;

import com.project.jhub.global.response.CommonResponse;
import com.project.jhub.user.application.UserService;
import com.project.jhub.user.dto.request.UserJoinRequestDto;
import com.project.jhub.user.dto.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonResponse> createUser(@Validated @RequestBody UserJoinRequestDto request) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(CREATED.value())
                .message("회원가입 성공")
                .body(userService.join(request))
                .build(), CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getUserList() {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("전체 조회 성공")
                .body(userService.findAll())
                .build(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("조회 성공")
                .body(userService.findById(id))
                .build(), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest updateRequest) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("유저 업데이트 성공")
                .body(userService.update(id, updateRequest))
                .build(), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteOne(@PathVariable Long id) {
        userService.deleteById(id);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("삭제 성공")
                .build(), OK);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteAll() {
        userService.deleteAll();

        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("삭제 성공")
                .build(), OK);
    }
}
