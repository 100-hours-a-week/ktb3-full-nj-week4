package com.example.dance_community.repository.in_memory;

import com.example.dance_community.entity.User;

import java.util.Optional;

public interface UserRepo {
    User saveUser(User user);
    boolean existsById(Long userId);
    Optional<User> findById(Long userId);
    Optional<User> findByEmail(String email);
    void deleteUser(Long userId);
}
