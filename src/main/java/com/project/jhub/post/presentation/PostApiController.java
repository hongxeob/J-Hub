package com.project.jhub.post.presentation;

import com.project.jhub.global.response.CommonResponse;
import com.project.jhub.post.application.PostService;
import com.project.jhub.post.dto.request.PostCreateRequest;
import com.project.jhub.post.dto.request.PostUpdateRequest;
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
@RequestMapping("/api/v1/posts")
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CommonResponse> writePost(@Validated @RequestBody PostCreateRequest createRequest, String username) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(CREATED.value())
                .message("게시물 작성 성공")
                .body(postService.write(createRequest))
                .build(), CREATED);
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getPostList() {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("전체 조회 성공")
                .body(postService.findAll())
                .build(), OK);
    }

    @GetMapping("/with-user")
    public ResponseEntity<CommonResponse> getPostListWithUser() {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("유저 정보와 함께 조회 성공")
                .body(postService.findAllWithUser())
                .build(), OK);
    }

    @GetMapping("/with-user/{id}")
    public ResponseEntity<CommonResponse> getPostWithUser(@PathVariable Long id) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("유저 정보와 함께 조회 성공")
                .body(postService.findByIdWithUser(id))
                .build(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getPost(@PathVariable Long id) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("조회 성공")
                .body(postService.findById(id))
                .build(), OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<CommonResponse> getPostsByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("USER ID로 게시물 조회 성공")
                .body(postService.findByUserId(id))
                .build(), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updatePost(@PathVariable Long id, PostUpdateRequest updateRequest) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("유저 업데이트 성공")
                .body(postService.update(id, updateRequest))
                .build(), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteOne(@PathVariable Long id) {
        postService.deleteById(id);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("삭제 성공")
                .build(), OK);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteAll() {
        postService.deleteAll();

        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("삭제 성공")
                .build(), OK);
    }
}