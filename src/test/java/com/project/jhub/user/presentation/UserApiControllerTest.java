package com.project.jhub.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.jhub.user.application.UserService;
import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRepository;
import com.project.jhub.user.domain.UserRole;
import com.project.jhub.user.dto.request.UserJoinRequestDto;
import com.project.jhub.user.dto.request.UserUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("유저 가입 성공")
    void createUserTest() throws Exception {

        //given
        UserJoinRequestDto userRequest = UserJoinRequestDto.builder()
                .username("LEEE")
                .nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();


        mockMvc.perform(post("/api/v1/user")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("유저 가입 실패 - 중복된ID")
    void createUserFailTest_DuplicatedId() throws Exception {

        //given
        User user = User.builder().username("LEEE").nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();
        userRepository.save(user);

        UserJoinRequestDto userRequest = UserJoinRequestDto.builder()
                .username("LEEE")
                .nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();


        mockMvc.perform(post("/api/v1/user")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("유저 가입 실패 - 중복된 닉네임")
    void createUserFailTest_DuplicatedNickname() throws Exception {

        //given
        User user = User.builder().username("LEEE2").nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();
        userRepository.save(user);

        UserJoinRequestDto userRequest = UserJoinRequestDto.builder()
                .username("LEEE")
                .nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();


        mockMvc.perform(post("/api/v1/user")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("유저 전체 조회")
    void findAllUserTest() throws Exception {

        //given
        User user = User.builder().username("LEEE2").nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();
        userRepository.save(user);

        mockMvc.perform(get("/api/v1/user/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("유저ID로 조회")
    void findByIdUserTest() throws Exception {

        //given
        User user = User.builder().username("LEEE2").nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();
        userRepository.save(user);

        mockMvc.perform(get("/api/v1/user/{id}", user.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.username").value(user.getUsername()))
                .andDo(print());
    }

    @Test
    @DisplayName("유저 업데이트 성공")
    void updateUserTest() throws Exception {

        User user = User.builder().username("LEEE2").nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();
        userRepository.save(user);

        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .nickname("abc123")
                .username(user.getUsername())
                .introduction("44년차444444444")
                .userRole(UserRole.MENTEE)
                .build();

        mockMvc.perform(patch("/api/v1/user/update", user.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.nickname").value(updateRequest.getNickname()))
                .andDo(print());
    }

    @Test
    @DisplayName("ID로 유저 삭제")
    void deleteByIdTest() throws Exception {

        User user = User.builder().username("LEEE2").nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();
        userRepository.save(user);

        mockMvc.perform(delete("/api/v1/user/{id}", user.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("ID로 유저 삭제")
    void deleteAllTest() throws Exception {

        User user = User.builder().username("LEEE2").nickname("섭123")
                .introduction("3년차 개발자 입니다.")
                .userRole(UserRole.MENTOR).build();
        userRepository.save(user);

        mockMvc.perform(delete("/api/v1/user")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
