package com.project.jhub.post.application;

import com.project.jhub.global.exception.BusinessException;
import com.project.jhub.global.exception.ErrorCode;
import com.project.jhub.post.domain.Category;
import com.project.jhub.post.domain.Post;
import com.project.jhub.post.domain.PostRepository;
import com.project.jhub.post.dto.request.PostCreateRequest;
import com.project.jhub.post.dto.request.PostUpdateRequest;
import com.project.jhub.post.dto.response.PostListResponse;
import com.project.jhub.post.dto.response.PostResponse;
import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.jhub.global.exception.ErrorCode.NOT_FOUND_POST;
import static com.project.jhub.global.exception.ErrorCode.NOT_MATCHED_USER_AND_POST;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse write(PostCreateRequest postCreateRequest) {
        User user = userRepository.findByUsername(postCreateRequest.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        postCreateRequest.insertUser(user);

        Post post = postCreateRequest.toEntity();
        postRepository.save(post);

        return post.toDto();
    }

    @Transactional(readOnly = true)
    public PostListResponse findAllWithUserAndComments(Pageable pageable) {
        Page<Post> posts = postRepository.findAllWithUserAndComments(pageable);

        return new PostListResponse(posts.stream()
                .map(Post::toDto)
                .toList());
    }

    @Transactional(readOnly = true)
    public PostResponse findByIdWithUserAndComments(Long id) {
        Post post = postRepository.findByIdWithUserAndComments(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));

        return post.toDto();
    }

    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));

        return post.toDto();
    }

    @Transactional(readOnly = true)
    public PostListResponse findByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        Page<Post> posts = postRepository.findByUserId(user.getId(), pageable);

        return new PostListResponse(posts.stream()
                .map(Post::toDto)
                .toList());
    }

    @Transactional(readOnly = true)
    public PostListResponse findByCategory(Category category, Pageable pageable) {
        Page<Post> posts = postRepository.findByCategory(category, pageable);

        return new PostListResponse(posts.stream()
                .map(Post::toDto)
                .toList());
    }

    @Transactional(readOnly = true)
    public PostListResponse findByTitle(String searchTitle, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitle(searchTitle, pageable);

        return new PostListResponse(posts.stream()
                .map(Post::toDto)
                .toList());
    }

    @Transactional
    public void deleteById(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));

        sameCheckAuthorAndModifier(post.getUser().getUsername(), username);

        postRepository.deleteById(post.getId());
    }

    @Transactional
    public void deleteAll() {
        postRepository.deleteAll();
    }

    @Transactional
    public PostResponse update(Long id, PostUpdateRequest updateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));

        sameCheckAuthorAndModifier(post.getUser().getUsername(), updateRequest.getUsername());

        post.updatePost(updateRequest);

        return post.toDto();
    }

    private void sameCheckAuthorAndModifier(String author, String modifier) {
        if (!author.equals(modifier)) {
            throw new BusinessException(NOT_MATCHED_USER_AND_POST);
        }
    }
}
