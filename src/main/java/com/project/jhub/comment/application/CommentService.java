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
import static com.project.jhub.global.exception.ErrorCode.NOT_MATCHED_USER_AND_POST;

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

        User user = userRepository.findByUsername(createRequest.getUsername())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        createRequest.insertPostAndAuthor(post, user);

        Comment comment = createRequest.toEntity();
        commentRepository.save(comment);

        return comment.toDto();
    }

    @Transactional(readOnly = true)
    public CommentResponse findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));

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
    public void deleteById(Long postId, Long commentId, String username) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));

        sameCheckAuthorAndModifier(comment.getUser().getUsername(), username);

        commentRepository.deleteById(comment.getId());
    }

    private void sameCheckAuthorAndModifier(String author, String modifier) {
        if (!author.equals(modifier)) {
            throw new BusinessException(NOT_MATCHED_USER_AND_POST);
        }
    }
}
