package com.example.dance_community.repository;

import com.example.dance_community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByNicknameAndUserIdNot(String nickname, Long userId);
}