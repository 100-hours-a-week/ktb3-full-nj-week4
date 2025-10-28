package com.example.dance_community.service;

import com.example.dance_community.dto.auth.*;
import com.example.dance_community.entity.User;
import com.example.dance_community.exception.AuthException;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.repository.UserRepo;
import com.example.dance_community.jwt.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepo userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    public AuthDto signup(SignupRequest signupRequest){
        if (userRepo.findByEmail(signupRequest.getEmail()).isPresent()){
            throw new ConflictException("이메일 중복");
        }

        User user = new User(signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getUsername());
        User newUser = userRepo.saveUser(user);
        return new AuthDto(newUser.getUserId());
    }

    public AuthDto login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthException("등록되지 않은 이메일"));

        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
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
