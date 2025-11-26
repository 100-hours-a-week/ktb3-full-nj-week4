package com.example.dance_community.service;

import com.example.dance_community.dto.user.PasswordUpdateRequest;
import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.dto.user.UserUpdateRequest;
import com.example.dance_community.entity.User;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(String email, String password, String nickname, String profileImage) {
        if (existsByEmail(email)) {
            throw new ConflictException("이미 존재하는 이메일입니다");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .profileImage(profileImage)
                .build();

        userRepository.save(user);
        return UserResponse.from(user);
    }

    public UserResponse getUser(Long userId) {
        User user = this.findByUserId(userId);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = this.findByUserId(userId);

        user.updateUser(
                request.getNickname(),
                request.getProfileImage() == null ? user.getProfileImage() : request.getProfileImage()
        );

        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updatePassword(Long userId, PasswordUpdateRequest request) {
        User user = this.findByUserId(userId);

        user.updatePassword(
                passwordEncoder.encode(request.getPassword())
        );
        return UserResponse.from(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findByUserId(userId);
        user.delete();
    }

    public boolean matchesPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
    public User findByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 사용자"));
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 사용자"));
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}