package com.project.jhub.user.application;

import com.project.jhub.global.exception.BusinessException;
import com.project.jhub.user.domain.User;
import com.project.jhub.user.domain.UserRepository;
import com.project.jhub.user.dto.request.UserJoinRequestDto;
import com.project.jhub.user.dto.request.UserUpdateRequest;
import com.project.jhub.user.dto.response.UserListResponse;
import com.project.jhub.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.jhub.global.exception.ErrorCode.DUPLICATED_NICKNAME;
import static com.project.jhub.global.exception.ErrorCode.DUPLICATED_USERNAME;
import static com.project.jhub.global.exception.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse join(UserJoinRequestDto request) {
        duplicatedUser(request.getUsername(), request.getNickname());

        User savedUser = userRepository.save(request.toEntity());

        return savedUser.toDto();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        return user.toDto();
    }

    @Transactional(readOnly = true)
    public UserListResponse findAll() {
        List<User> userList = userRepository.findAll();

        List<UserResponse> userResponseList = userList.stream().map(UserResponse::new).toList();

        return new UserListResponse(userResponseList);
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        user.updateUser(updateRequest);

        return user.toDto();
    }

    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        userRepository.deleteById(user.getId());
    }

    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }

    private void duplicatedUser(String username, String nickname) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new BusinessException(DUPLICATED_USERNAME);
        });

        userRepository.findByNickname(nickname).ifPresent(user -> {
            throw new BusinessException(DUPLICATED_NICKNAME);
        });
    }
}
