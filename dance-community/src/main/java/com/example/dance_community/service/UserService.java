package com.example.dance_community.service;

import com.example.dance_community.dto.user.UserDto;
import com.example.dance_community.dto.user.UserRequest;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.UserRepository;
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
        userDto.setPassword(null);
        return userDto;
    }

    public UserDto updateUser(Long userId, UserRequest userRequest) {
        UserDto userDto = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("회원 정보 수정 실패"));
        if(userRequest.getPassword() != null) userDto.setPassword(userRequest.getPassword());
        if(userRequest.getUsername() != null) userDto.setUsername(userRequest.getUsername());
        if(userRequest.getClubId() != null) userDto.setClubId(userRequest.getClubId());
        if(userRequest.getProfileImage() != null) userDto.setProfileImage(userRequest.getProfileImage());
        userRepository.saveUser(userDto);
        userDto.setPassword(null);
        return userDto;
    }

    public void deleteCurrentUser(Long userId) {
        UserDto userDto = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("회원 삭제 실패"));
        userRepository.deleteUser(userId);
    }
}
