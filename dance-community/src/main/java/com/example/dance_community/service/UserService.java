package com.example.dance_community.service;

import com.example.dance_community.dto.user.UserDto;
import com.example.dance_community.dto.user.UserRequest;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private UserDto getUser(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자 인증 실패"));
    }

    public UserDto getUserById(Long userId) {
        return UserDto.changePassword(getUser(userId));
    }

    public UserDto updateUser(Long userId, UserRequest userRequest) {
        UserDto userDto = getUser(userId);

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
        return UserDto.changePassword(updatedUser);
    }

    public void deleteCurrentUser(Long userId) {
        getUser(userId);
        userRepo.deleteUser(userId);
    }
}