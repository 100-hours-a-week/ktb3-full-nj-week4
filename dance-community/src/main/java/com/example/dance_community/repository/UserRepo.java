package com.example.dance_community.repository;

import com.example.dance_community.dto.user.UserDto;

import java.util.Optional;

public interface UserRepo {
    UserDto saveUser(UserDto userDto);
    boolean existsById(Long userId);
    Optional<UserDto> findById(Long userId);
    Optional<UserDto> findByEmail(String email);
    void deleteUser(Long userId);
}
