package com.example.dance_community.service;

import com.example.dance_community.dto.user.UserDto;
import com.example.dance_community.dto.user.UserRequest;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserById(Long userId) {
        UserDto userDto = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("회원 정보 조회 실패"));
        return UserDto.changePassword(userDto);
    }

    public UserDto updateUser(Long userId, UserRequest userRequest) {
        UserDto userDto = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("회원 정보 수정 실패"));

        String newPassword = userRequest.getPassword() != null
                ? BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt())
                : null;

        UserDto updatedUser = userDto.toBuilder()
                .password(newPassword != null ? newPassword : userDto.getPassword())
                .username(userRequest.getUsername() != null ? userRequest.getUsername() : userDto.getUsername())
                .clubId(userRequest.getClubId() != null ? userRequest.getClubId() : userDto.getClubId())
                .profileImage(userRequest.getProfileImage() != null ? userRequest.getProfileImage() : userDto.getProfileImage())
                .build();

        userRepository.saveUser(updatedUser);
        return UserDto.changePassword(updatedUser);
    }

    public void deleteCurrentUser(Long userId) {
        UserDto userDto = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("회원 삭제 실패"));
        userRepository.deleteUser(userId);
    }
}
