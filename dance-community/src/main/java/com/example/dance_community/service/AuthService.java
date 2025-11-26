package com.example.dance_community.service;

import com.example.dance_community.dto.auth.*;
import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.entity.User;
import com.example.dance_community.exception.AuthException;
import com.example.dance_community.security.CookieUtil;
import com.example.dance_community.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final CookieUtil cookieUtil;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponse signup(SignupRequest request){
        UserResponse userResponse = userService.createUser(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getProfileImage()
        );

        return new AuthResponse(userResponse, "");
    }

    public AuthResponse login(LoginRequest request, HttpServletResponse response) {
        User user = userService.findByEmail(request.getEmail());

        if (!userService.matchesPassword(user, request.getPassword())) {
            throw new AuthException("비밀번호가 일치하지 않습니다");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

        cookieUtil.setRefreshTokenCookie(response, refreshToken);

        return new AuthResponse(UserResponse.from(user), accessToken);
    }

    public AuthResponse refresh(String refreshToken) {
        if (refreshToken == null) {
            throw new AuthException("refreshToken이 없습니다");
        }

        if (!jwtUtil.validateToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            throw new AuthException("유효하지 않은 refreshToken");
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        User user = userService.findByUserId(userId);

        String newAccessToken = jwtUtil.generateAccessToken(userId);
        return new AuthResponse(UserResponse.from(user), newAccessToken);
    }

    public void logout(HttpServletResponse response) {
        cookieUtil.deleteRefreshTokenCookie(response);
    }
}
