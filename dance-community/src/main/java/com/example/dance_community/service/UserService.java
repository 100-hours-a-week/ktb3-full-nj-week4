package com.example.dance_community.service;

import com.example.dance_community.dto.user.PasswordUpdateRequest;
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

    public UserResponse getUser(Long userId) {
        User user = this.getActiveUser(userId);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = this.getActiveUser(userId);

        user.updateUser(
                request.getNickname(),
                request.getProfileImage() == null ? user.getProfileImage() : request.getProfileImage()
        );

        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updatePassword(Long userId, PasswordUpdateRequest request) {
        User user = this.getActiveUser(userId);

        user.updatePassword(
                passwordEncoder.encode(request.getPassword())
        );
        return UserResponse.from(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = getActiveUser(userId);
        user.delete();
    }

    public User getActiveUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 사용자"));
    }
}