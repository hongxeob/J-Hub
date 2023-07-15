package com.project.jhub.post.application;

import com.project.jhub.global.exception.BusinessException;
import com.project.jhub.post.domain.Post;
import com.project.jhub.post.domain.PostRepository;
import com.project.jhub.post.dto.request.PostCreateRequest;
import com.project.jhub.post.dto.request.PostUpdateRequest;
import com.project.jhub.post.dto.response.PostListResponse;
import com.project.jhub.post.dto.response.PostResponse;
import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRepository;
import com.project.jhub.user.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    User savedUser;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .username("AAAAAAA")
                .nickname("NININININ")
                .introduction("HAHAHAHAHAHAHAHAH")
                .userRole(UserRole.MENTEE)
                .build();

        savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("게시물 저장 성공")
    void writePostSuccessTest() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("첫번째게시물등장")
                .content("spring Guultip 쏩니다.")
                .username(savedUser.getUsername())
                .build();

        //when
        PostResponse savedPost = postService.write(postCreateRequest);

        //then
        assertThat(savedPost.getContent()).isEqualTo(postCreateRequest.getContent());
        assertThat(savedPost.getUserResponse().getUsername()).isEqualTo(savedUser.getUsername());
    }

    @Test
    @DisplayName("게시물 저장 실패 - 존재하지 않는 유저")
    void writePostFailTest() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("첫번째게시물등장")
                .content("spring Guultip 쏩니다.")
                .username("asdf")
                .build();

        //when -> then
        assertThrows(BusinessException.class, () -> postService.write(postCreateRequest));
    }

    @Test
    @DisplayName("게시물 전체 조회")
    void findAllPostTest() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("첫번째게시물등장")
                .content("spring Guultip 쏩니다.")
                .username(savedUser.getUsername())
                .build();

        postService.write(postCreateRequest);

        //when
        PostListResponse postWithUserListResponse = postService.findAllWithUserAndComments();

        //then
        assertThat(postWithUserListResponse.getPostResponseList().isEmpty()).isEqualTo(false);
    }

    @Test
    @DisplayName("ID로 게시물 조회 성공")
    void findByIdSuccessTest() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("첫번째게시물등장")
                .content("spring Guultip 쏩니다.")
                .username(savedUser.getUsername())
                .build();

        PostResponse savedPost = postService.write(postCreateRequest);

        //when
        PostResponse postWithUserResponse = postService.findByIdWithUserAndComments(savedPost.getId());

        //then
        assertThat(postWithUserResponse.getTitle()).isEqualTo(postCreateRequest.getTitle());
        assertThat(postWithUserResponse.getUserResponse().getUsername()).isEqualTo(savedPost.getUserResponse().getUsername());
    }

    @Test
    @DisplayName("ID로 게시물 조회 실패 - 찾는 게시물 없음")
    void findByIdFailTest() throws Exception {

        //given
        Post post = Post.builder()
                .id(2L)
                .title("asdfasdfasdf")
                .content("asdfasdfadsf")
                .user(savedUser)
                .build();

        //when -> then
        assertThrows(BusinessException.class, () -> postService.findByIdWithUserAndComments(post.getId()));
    }

    @Test
    @DisplayName("게시물 업데이트 성공")
    void updatePostSuccessTest() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("첫번째게시물등장")
                .content("spring Guultip 쏩니다.")
                .username(savedUser.getUsername())
                .build();

        PostResponse savedPost = postService.write(postCreateRequest);

        PostUpdateRequest updateRequest = PostUpdateRequest.builder()
                .title("두번째게시물등장")
                .content("django 꿀팁 쏩니다")
                .build();

        //when
        PostResponse updatedPost = postService.update(savedPost.getId(), updateRequest);

        //then
        assertThat(savedPost.getId()).isEqualTo(updatedPost.getId());
        assertThat(updatedPost.getTitle()).isEqualTo(updateRequest.getTitle());
    }

    @Test
    @DisplayName("게시물 수정 실패 - 존재하지 않는 게시물")
    void updatePostFailTest() throws Exception {

        //given
        Post post = Post.builder()
                .id(2L)
                .title("asdfasdfasdf")
                .content("asdfasdfadsf")
                .user(savedUser)
                .build();

        PostUpdateRequest updateRequest = PostUpdateRequest.builder()
                .title("두번째게시물등장")
                .content("django 꿀팁 쏩니다")
                .build();

        //when -> then
        assertThrows(BusinessException.class, () -> postService.update(post.getId(), updateRequest));
    }

    @Test
    @DisplayName("ID로 게시물 삭제 성공")
    void deleteByIdSuccessTest() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("첫번째게시물등장")
                .content("spring Guultip 쏩니다.")
                .username(savedUser.getUsername())
                .build();

        PostResponse savedPost = postService.write(postCreateRequest);

        //when
        postService.deleteById(savedPost.getId());

        //then
        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(0);
        assertThat(posts.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("ID로 게시물 삭제 실패 - 존재하지 않는 게시물")
    void deleteByIdFailTest() throws Exception {

        //given
        Post post = Post.builder()
                .id(2L)
                .title("asdfasdfasdf")
                .content("asdfasdfadsf")
                .user(savedUser)
                .build();

        //when -> then
        assertThrows(BusinessException.class, () -> postService.deleteById(post.getId()));
    }

    @Test
    @DisplayName("게시물 전체 삭제")
    void deleteAllTest() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("첫번째게시물등장")
                .content("spring Guultip 쏩니다.")
                .username(savedUser.getUsername())
                .build();

        PostResponse savedPost = postService.write(postCreateRequest);

        //when
        postService.deleteAll();

        //then
        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(0);
        assertThat(posts.isEmpty()).isEqualTo(true);
    }
}