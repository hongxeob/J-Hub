package com.project.jhub.comment.application;

import com.project.jhub.comment.domain.Comment;
import com.project.jhub.comment.domain.CommentRepository;
import com.project.jhub.comment.dto.request.CommentCreateRequest;
import com.project.jhub.comment.dto.request.CommentUpdateRequest;
import com.project.jhub.comment.dto.response.CommentResponse;
import com.project.jhub.post.domain.Post;
import com.project.jhub.post.domain.PostRepository;
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

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User savedUser;
    Post savedPost;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .username("AAAAAAA")
                .nickname("NININININ")
                .introduction("HAHAHAHAHAHAHAHAH")
                .userRole(UserRole.MENTEE)
                .build();

        savedUser = userRepository.save(user);

        Post post = Post.builder()
                .title("abcabcabcabc")
                .content("Asdfasdfasdfasdf")
                .user(user)
                .build();

        savedPost = postRepository.save(post);
    }

    @Test
    @DisplayName("댓글 작성 성공")
    void writeCommentTest() throws Exception {

        //given
        CommentCreateRequest commentRequest = CommentCreateRequest.builder()
                .content("댓글작성")
                .username(savedUser.getUsername())
                .build();

        //when
        CommentResponse savedComment = commentService.write(savedPost.getId(), commentRequest);

        //then
        assertThat(savedComment.getContent()).isEqualTo(commentRequest.getContent());
        assertThat(savedComment.getNickname()).isEqualTo(savedUser.getNickname());
        assertThat(savedComment.getPostId()).isEqualTo(savedPost.getId());
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void updateCommentTest() throws Exception {

        //given
        CommentCreateRequest commentRequest = CommentCreateRequest.builder()
                .content("댓글작성")
                .username(savedUser.getUsername())
                .build();

        CommentResponse savedComment = commentService.write(savedPost.getId(), commentRequest);
        CommentUpdateRequest updateRequest = new CommentUpdateRequest("BBBBBBBBBBBBBB");

        //when
        CommentResponse updatedComment = commentService.update(savedPost.getId(), savedComment.getId(), updateRequest);

        //then
        assertThat(updateRequest.getContent()).isEqualTo(updatedComment.getContent());
        assertThat(updatedComment.getPostId()).isEqualTo(savedPost.getId());
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteCommentByIdTest() throws Exception {

        //given
        CommentCreateRequest commentRequest = CommentCreateRequest.builder()
                .content("댓글작성")
                .username(savedUser.getUsername())
                .build();

        CommentResponse savedComment =
                commentService.write(savedPost.getId(), commentRequest);

        //when

        commentService.deleteById(savedPost.getId(), savedComment.getId(), savedComment.getUsername());

        //then
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments.isEmpty()).isEqualTo(true);
    }
}