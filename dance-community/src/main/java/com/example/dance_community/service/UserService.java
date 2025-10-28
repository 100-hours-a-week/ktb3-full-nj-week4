package com.example.dance_community.service;

import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.dto.user.UserUpdateRequest;
import com.example.dance_community.encoder.PasswordEncoder;
import com.example.dance_community.entity.User;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.jpa.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserById(Long userId) {
        User user = this.getUserEntityById(userId);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = this.getUserEntityById(userId);
        user.updateUser(
                userUpdateRequest.username(),
                passwordEncoder.encode(userUpdateRequest.password()),
                userUpdateRequest.profileImage()
        );
        return UserResponse.from(user);
    }

    @Transactional
    public void deleteCurrentUser(Long userId) {
        User user = this.getUserEntityById(userId);
        user.deleteUser();
    }

    private User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 사용자"));
    }
}