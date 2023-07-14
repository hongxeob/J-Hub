package com.project.jhub.comment.application;

import com.project.jhub.comment.domain.Comment;
import com.project.jhub.comment.domain.CommentRepository;
import com.project.jhub.comment.dto.request.CommentCreateRequest;
import com.project.jhub.comment.dto.request.CommentUpdateRequest;
import com.project.jhub.comment.dto.response.CommentResponse;
import com.project.jhub.global.exception.BusinessException;
import com.project.jhub.post.domain.Post;
import com.project.jhub.post.domain.PostRepository;
import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.jhub.global.exception.ErrorCode.NOT_FOUND_COMMENT;
import static com.project.jhub.global.exception.ErrorCode.NOT_FOUND_POST;
import static com.project.jhub.global.exception.ErrorCode.NOT_FOUND_USER;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse write(Long postId, CommentCreateRequest createRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));

        User user = userRepository.findByNickname(createRequest.getNickname())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        createRequest.insertPostAndAuthor(post, user);

        Comment comment = createRequest.toEntity();
        commentRepository.save(comment);

        return comment.toDto();
    }

    @Transactional
    public CommentResponse update(Long postId, Long commentId, CommentUpdateRequest updateRequest) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));

        comment.updateComment(updateRequest);

        return comment.toDto();
    }

    @Transactional
    public void deleteById(Long postId, Long commentId) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));

        commentRepository.deleteById(comment.getId());
    }
}
