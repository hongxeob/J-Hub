package com.project.jhub.comment.presentation;

import com.project.jhub.comment.application.CommentService;
import com.project.jhub.comment.dto.request.CommentCreateRequest;
import com.project.jhub.comment.dto.request.CommentUpdateRequest;
import com.project.jhub.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentApiController {

    private final CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommonResponse> writeComment(@PathVariable Long postId, @RequestBody CommentCreateRequest createRequest) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("댓글 작성 성공")
                .body(commentService.write(postId, createRequest))
                .build(), OK);
    }

    @PatchMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommonResponse> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                        @RequestBody CommentUpdateRequest updateRequest) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("댓글 업데이트 성공")
                .body(commentService.update(postId, commentId, updateRequest))
                .build(), OK);
    }

    @DeleteMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommonResponse> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteById(postId, commentId);

        return new ResponseEntity<>(CommonResponse.builder()
                .status(OK.value())
                .message("댓글 삭제 성공")
                .build(), OK);
    }
}
