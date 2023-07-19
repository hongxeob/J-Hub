package com.project.jhub.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.jhub.global.exception.BusinessException;
import com.project.jhub.post.application.PostService;
import com.project.jhub.post.domain.Category;
import com.project.jhub.post.domain.Post;
import com.project.jhub.post.domain.PostRepository;
import com.project.jhub.post.dto.request.PostCreateRequest;
import com.project.jhub.post.dto.response.PostListResponse;
import com.project.jhub.post.dto.response.PostResponse;
import com.project.jhub.user.application.UserService;
import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.project.jhub.global.exception.ErrorCode.INVALID_INPUT_VALUE;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PostApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @MockBean
    UserService userService;

    @MockBean
    PostRepository postRepository;

    User user = null;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("hong")
                .nickname("asdfasdf")
                .introduction("asdfasdfasdf")
                .userRole(UserRole.MENTOR)
                .build();
    }

    @Test
    @DisplayName("게시물 생성 성공")
    void createPostSuccessTest() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("게시물 생성 테스트")
                .content("게시물 생성 테스트")
                .category(Category.QNA)
                .username(user.getUsername())
                .build();

        //when -> then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 생성 실패 제목 없음")
    void createPostFailTest_nullTitle() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title(null)
                .content("게시물 생성 테스트")
                .category(Category.QNA)
                .username(user.getUsername())
                .build();

        given(postService.write(postCreateRequest))
                .willThrow(new BusinessException(INVALID_INPUT_VALUE));

        //when -> then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 생성 실패 - 내용 없음")
    void createPostFailTest_nullContent() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("제목 생성중")
                .content(null)
                .category(Category.QNA)
                .username(user.getUsername())
                .build();

        given(postService.write(postCreateRequest))
                .willThrow(new BusinessException(INVALID_INPUT_VALUE));

        //when -> then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 전체 조회")
    void findAllPostTest() throws Exception {

        //given
        Post post1 = Post.builder()
                .id(1L)
                .title("asdfassdf")
                .content("asdfasdf")
                .category(Category.QNA)
                .user(user)
                .build();

        Post post2 = Post.builder()
                .id(1L)
                .title("asdfassdf")
                .content("asdfasdf")
                .category(Category.QNA)
                .user(user)
                .build();

        List<Post> posts = Arrays.asList(post1, post2);
        PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        given(postService.findAllWithUserAndComments(pageRequest))
                .willReturn(new PostListResponse(posts.stream()
                        .map(PostResponse::new)
                        .toList()));

        //then
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물ID로 게시물 조회 성공")
    void findByIdSuccessTest() throws Exception {

        //given
        Post post = Post.builder()
                .id(1L)
                .title("asdfassdf")
                .content("asdfasdf")
                .category(Category.QNA)
                .user(user)
                .build();

        //when
        given(postService.findById(post.getId()))
                .willReturn(post.toDto());

        //then
        mockMvc.perform(get("/api/v1/posts/{id}", post.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("유저ID로 게시물 조회 성공")
    void findByUserIdTest() throws Exception {

        //given
        Post post1 = Post.builder()
                .id(1L)
                .title("asdfassdf")
                .content("asdfasdf")
                .category(Category.QNA)
                .user(user)
                .build();

        Post post2 = Post.builder()
                .id(1L)
                .title("asdfassdf")
                .content("asdfasdf")
                .category(Category.QNA)
                .user(user)
                .build();

        List<Post> posts = Arrays.asList(post1, post2);
        PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        given(postService.findByUserId(user.getId(), pageRequest))
                .willReturn(new PostListResponse(posts.stream()
                        .map(PostResponse::new)
                        .toList()));
        //then
        mockMvc.perform(get("/api/v1/posts/user/{id}", user.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 ID로 단건 삭제")
    void deleteByIdFailTest() throws Exception {

        //given
        Post post = Post.builder()
                .id(1L)
                .title("asdfassdf")
                .content("asdfasdf")
                .category(Category.QNA)
                .user(user)
                .build();
        PageRequest pageRequest = PageRequest.of(0, 3);

        //then
        mockMvc.perform(delete("/api/v1/posts/{id}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물 전체 삭제")
    void deleteAllTest() throws Exception {

        //given -> when -> then
        mockMvc.perform(delete("/api/v1/posts"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}