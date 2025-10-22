package com.example.dance_community.repository;

import com.example.dance_community.dto.user.UserDto;

import java.util.Optional;

public interface UserRepository {
    UserDto saveUser(UserDto userDto);
    Optional<UserDto> findById(Long userId);
    Optional<UserDto> findByEmail(String email);
    void deleteUser(Long userId);
}
