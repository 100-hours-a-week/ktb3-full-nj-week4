package com.example.dance_community.service;

import com.example.dance_community.dto.user.UserDto;
import com.example.dance_community.dto.user.UserRequest;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDto getUserById(Long userId) {
        return UserDto.changePassword(userRepo.findById(userId));
    }

    public UserDto updateUser(Long userId, UserRequest userRequest) {
        UserDto userDto = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 사용자"));

        String newPassword = userRequest.getPassword() != null
                ? BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt())
                : userDto.getPassword();

        UserDto updatedUser = userDto.toBuilder()
                .password(newPassword)
                .username(userRequest.getUsername() != null ? userRequest.getUsername() : userDto.getUsername())
                .clubId(userRequest.getClubId() != null ? userRequest.getClubId() : userDto.getClubId())
                .profileImage(userRequest.getProfileImage() != null ? userRequest.getProfileImage() : userDto.getProfileImage())
                .build();

        userRepo.saveUser(updatedUser);
        return UserDto.changePassword(Optional.ofNullable(updatedUser));
    }

    public void deleteCurrentUser(Long userId) {
        userRepo.deleteUser(userId);
    }
}