package com.project.jhub.post.application;

import com.project.jhub.global.exception.BusinessException;
import com.project.jhub.global.exception.ErrorCode;
import com.project.jhub.post.domain.Post;
import com.project.jhub.post.domain.PostRepository;
import com.project.jhub.post.dto.request.PostCreateRequest;
import com.project.jhub.post.dto.request.PostUpdateRequest;
import com.project.jhub.post.dto.response.PostListResponse;
import com.project.jhub.post.dto.response.PostResponse;
import com.project.jhub.post.dto.response.PostWithUserListResponse;
import com.project.jhub.post.dto.response.PostWithUserResponse;
import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostWithUserResponse write(PostCreateRequest postCreateRequest) {
        User user = userRepository.findByUsername(postCreateRequest.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        postCreateRequest.setUser(user);
        Post post = postCreateRequest.toEntity();
        postRepository.save(post);

        return post.toWithUserDto();
    }

    @Transactional(readOnly = true)
    public PostListResponse findAll() {
        List<Post> posts = postRepository.findAll();

        return new PostListResponse(posts.stream()
                .map(Post::toDto)
                .toList());
    }

    @Transactional(readOnly = true)
    public PostWithUserListResponse findAllWithUser() {
        List<Post> posts = postRepository.findAllWithUser();

        return new PostWithUserListResponse(posts.stream()
                .map(Post::toWithUserDto)
                .toList());
    }

    @Transactional(readOnly = true)
    public PostWithUserResponse findByIdWithUser(Long id) {
        Post post = postRepository.findByIdWithUser(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        return post.toWithUserDto();
    }

    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        return post.toDto();
    }

    @Transactional(readOnly = true)
    public PostWithUserListResponse findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        List<Post> posts = postRepository.findByUserId(user.getId());

        return new PostWithUserListResponse(posts.stream()
                .map(Post::toWithUserDto)
                .toList());
    }

    @Transactional
    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        postRepository.deleteById(post.getId());
    }

    @Transactional
    public void deleteAll() {
        postRepository.deleteAll();
    }

    @Transactional
    public PostWithUserResponse update(Long id, PostUpdateRequest updateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        post.updatePost(updateRequest);
        return post.toWithUserDto();
    }
}
