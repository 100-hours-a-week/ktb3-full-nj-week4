package com.example.dance_community.service;

import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.dto.user.UserUpdateRequest;
import com.example.dance_community.encoder.PasswordEncoder;
import com.example.dance_community.entity.User;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 사용자"));
        return UserResponse.from(user);
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 사용자"));

        User updatedUser = user.updateUser(
                passwordEncoder.encode(userUpdateRequest.password()),
                userUpdateRequest.username(),
                userUpdateRequest.profileImage()
        );

        return UserResponse.from(userRepo.saveUser(updatedUser));
    }

    public void deleteCurrentUser(Long userId) {
        userRepo.deleteUser(userId);
    }
}