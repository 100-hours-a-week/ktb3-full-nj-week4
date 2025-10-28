package com.example.dance_community.service;

import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.dto.user.UserUpdateRequest;
import com.example.dance_community.entity.User;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 사용자"));
        return UserResponse.from(user);
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 사용자"));
        User updatedUser = user.toBuilder()
                .password(userUpdateRequest.password() != null ? userUpdateRequest.password() : user.getPassword())
                .username(userUpdateRequest.username() != null ? userUpdateRequest.username() : user.getUsername())
                .profileImage(userUpdateRequest.profileImage() != null ? userUpdateRequest.profileImage() : user.getProfileImage())
                .build();
        return UserResponse.from(userRepo.saveUser(updatedUser));
    }

    public void deleteCurrentUser(Long userId) {
        userRepo.deleteUser(userId);
    }
}