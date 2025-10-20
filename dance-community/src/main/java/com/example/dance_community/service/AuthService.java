package com.example.dance_community.service;

import com.example.dance_community.dto.auth.*;
import com.example.dance_community.dto.user.UserDto;
import com.example.dance_community.exception.AuthException;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.UserRepository;
import com.example.dance_community.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthDto signup(SignupRequest signupRequest){
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()){
            throw new ConflictException("이메일 중복");
        }

        UserDto user = UserDto.builder()
                .email(signupRequest.getEmail())
                .password(BCrypt.hashpw(signupRequest.getPassword(), BCrypt.gensalt()))
                .username(signupRequest.getUsername())
                .build();

        userRepository.saveUser(user);
        return new AuthDto(user.getUserId());
    }

    public AuthDto login(LoginRequest loginRequest) {
        UserDto user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthException("등록되지 않은 이메일"));

        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthException("비밀번호 미일치");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

        return new AuthDto(user.getUserId(), accessToken, refreshToken);
    }

    public AuthDto refreshAccessToken(String nowRefreshToken) {
        if (nowRefreshToken == null || nowRefreshToken.isBlank()) {
            throw new InvalidRequestException("토큰 미입력");
        }

        if (!jwtUtil.validateToken(nowRefreshToken)) {
            throw new AuthException("유효하지 않은 토큰");
        }

        if (!"refresh".equals(jwtUtil.getTokenType(nowRefreshToken))) {
            throw new AuthException("다른 토큰 입력");
        }

        String userIdStr = jwtUtil.getUserId(nowRefreshToken);
        Long userId;
        try {
            userId = Long.valueOf(userIdStr);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("토큰 해석 실패");
        }

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("사용자 인증 실패");
        }

        String newAccessToken = jwtUtil.generateAccessToken(userId);

        return new AuthDto(userId, newAccessToken, nowRefreshToken);
    }
}
