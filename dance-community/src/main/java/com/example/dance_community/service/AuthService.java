package com.example.dance_community.service;

import com.example.dance_community.dto.auth.*;
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
    public AuthDto signup(SignupRequest signupRequest){
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new ConflictException("이메일 중복");
        }
        User user = new User(signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getUsername());
        User newUser = userRepository.save(user);
        return new AuthDto(newUser.getUserId());
    }

    public AuthDto login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthException("등록되지 않은 이메일"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthException("비밀번호 미일치");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

        return new AuthDto(user.getUserId(), accessToken, refreshToken);
    }

    public AuthDto refreshAccessToken(Long userId) {
        String newAccessToken = jwtUtil.generateAccessToken(userId);
        String newRefreshToken = jwtUtil.generateRefreshToken(userId);

        return new AuthDto(userId, newAccessToken, newRefreshToken);
    }
}
