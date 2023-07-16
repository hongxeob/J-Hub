package com.project.jhub.user.application;

import com.project.jhub.global.exception.BusinessException;
import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRepository;
import com.project.jhub.user.domain.UserRole;
import com.project.jhub.user.dto.request.UserJoinRequestDto;
import com.project.jhub.user.dto.request.UserUpdateRequest;
import com.project.jhub.user.dto.response.UserListResponse;
import com.project.jhub.user.dto.response.UserResponse;
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
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("유저 저장 성공")
    void joinSuccessTest() throws Exception {

        //given
        UserJoinRequestDto userRequest = UserJoinRequestDto.builder()
                .username("User")
                .introduction("저는 3년차 개발자 입니다.")
                .nickname("Wㅏㅇ짱")
                .userRole(UserRole.MENTEE)
                .build();

        //when
        UserResponse user = userService.join(userRequest);

        //then
        List<User> userList = userRepository.findAll();
        assertThat(userList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("회원 저장 실패 - 중복된 username")
    void joinFailTest_username() throws Exception {

        //given
        UserJoinRequestDto userRequest1 = UserJoinRequestDto.builder()
                .username("User")
                .introduction("저는 3년차 개발자 입니다.")
                .nickname("Wㅏㅇ짱")
                .userRole(UserRole.MENTEE)
                .build();

        UserJoinRequestDto userRequest2 = UserJoinRequestDto.builder()
                .username("User")
                .introduction("저는 3년차 개발자 입니다.")
                .nickname("AA")
                .userRole(UserRole.MENTEE)
                .build();

        UserResponse user = userService.join(userRequest1);

        //when -> then
        assertThrows(BusinessException.class, () -> userService.join(userRequest2));
    }

    @Test
    @DisplayName("유저 저장 실패 - 중복된 nickname")
    void joinFailTest_nickname() throws Exception {

        //given
        UserJoinRequestDto userRequest1 = UserJoinRequestDto.builder()
                .username("User1")
                .introduction("저는 3년차 개발자 입니다.")
                .nickname("AA")
                .userRole(UserRole.MENTEE)
                .build();

        UserJoinRequestDto userRequest2 = UserJoinRequestDto.builder()
                .username("User2")
                .introduction("저는 3년차 개발자 입니다.")
                .nickname("AA")
                .userRole(UserRole.MENTEE)
                .build();

        UserResponse user = userService.join(userRequest1);

        //when -> then
        assertThrows(BusinessException.class, () -> userService.join(userRequest2));
    }

    @Test
    @DisplayName("유저 전체 조회")
    void findAllSuccessTest() throws Exception {

        //given
        UserJoinRequestDto userRequest1 = UserJoinRequestDto.builder()
                .username("User1")
                .introduction("저는 3년차 개발자 입니다.")
                .nickname("AA")
                .userRole(UserRole.MENTEE)
                .build();

        UserJoinRequestDto userRequest2 = UserJoinRequestDto.builder()
                .username("User2")
                .introduction("저는 3년차 개발자 입니다.")
                .nickname("BB")
                .userRole(UserRole.MENTEE)
                .build();

        UserResponse user1 = userService.join(userRequest1);
        UserResponse user2 = userService.join(userRequest2);

        //when
        UserListResponse userListResponse = userService.findAll();

        //then
        assertThat(userListResponse.getUserResponseList().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저 단건 조회 성공 - ID로 조회")
    void findByIdSuccessTest() throws Exception {

        //given
        User user = User.builder()
                .username("AA")
                .nickname("ddd")
                .introduction("Asdfasdf")
                .userRole(UserRole.MENTEE)
                .build();

        userRepository.save(user);

        //when
        UserResponse foundUser = userService.findById(user.getId());

        //then
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("유저 단건 조회 실패 - 존재하지 않는 유저")
    void findByIdFailTest() throws Exception {

        //when -> then
        assertThrows(BusinessException.class, () -> userService.findById(1L));
    }

    @Test
    @DisplayName("유저 업데이트 성공")
    void updateUserSuccessTest() throws Exception {

        //given
        User user = User.builder()
                .username("AA")
                .nickname("ddd")
                .introduction("Asdfasdf")
                .userRole(UserRole.MENTEE)
                .build();

        User savedUser = userRepository.save(user);

        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .username(savedUser.getUsername())
                .userRole(UserRole.MENTOR)
                .introduction("bbbb")
                .nickname("ccc").build();

        //when
        userService.update(updateRequest);

        //then
        User foundUser = userRepository.findById(savedUser.getId()).get();
        assertThat(foundUser.getUsername()).isEqualTo("AA");
        assertThat(foundUser.getNickname()).isEqualTo(updateRequest.getNickname());
    }

    @Test
    @DisplayName("유저 업데이트 실패 - 존재하지 않는 유저")
    void updateFailTest_notFound() throws Exception {

        //given
        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .username("asdf")
                .userRole(UserRole.MENTOR)
                .introduction("bbbb")
                .nickname("ccc").build();

        //when -> then
        assertThrows(BusinessException.class, () -> userService.update( updateRequest));
    }

    @Test
    @DisplayName("id로 유저 삭제")
    void deleteByIdSuccessTest() throws Exception {

        //given
        User user = User.builder()
                .username("AA")
                .nickname("ddd")
                .introduction("Asdfasdf")
                .userRole(UserRole.MENTEE)
                .build();

        User savedUser = userRepository.save(user);

        //when
        userService.deleteById(savedUser.getId());

        //then
        assertThat(userRepository.findById(savedUser.getId()).isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("유저 전체 삭제")
    void deleteAllTest() throws Exception {

        //given
        User user = User.builder()
                .username("AA")
                .nickname("ddd")
                .introduction("Asdfasdf")
                .userRole(UserRole.MENTEE)
                .build();

        User savedUser = userRepository.save(user);

        //when
        userService.deleteAll();

        //then
        assertThat(userRepository.findAll().size()).isEqualTo(0);
    }
}