package com.example.dance_community.service;

import com.example.dance_community.dto.auth.*;
import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.encoder.PasswordEncoder;
import com.example.dance_community.entity.User;
import com.example.dance_community.exception.AuthException;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.jwt.JwtUtil;
import com.example.dance_community.repository.jpa.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse signup(SignupRequest signupRequest){
        if (userRepository.existsByEmail(signupRequest.email())) {
            throw new ConflictException("이메일 중복");
        }
        User user = User.builder()
                .email(signupRequest.email())
                .password(passwordEncoder.encode(signupRequest.password()))
                .username(signupRequest.username())
                .build();
        User newUser = userRepository.save(user);
        return new AuthResponse(UserResponse.from(user), "", "");
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new AuthException("등록되지 않은 이메일"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new AuthException("비밀번호 미일치");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

        return new AuthResponse(UserResponse.from(user), accessToken, refreshToken);
    }

    public AuthResponse refreshAccessToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("등록되지 않은 사용자"));

        String newAccessToken = jwtUtil.generateAccessToken(userId);
        String newRefreshToken = jwtUtil.generateRefreshToken(userId);

        return new AuthResponse(UserResponse.from(user), newAccessToken, newRefreshToken);
    }
}
