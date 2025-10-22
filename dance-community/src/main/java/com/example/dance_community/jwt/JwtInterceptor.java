package com.example.dance_community.jwt;

import com.example.dance_community.exception.AuthException;
import com.example.dance_community.repository.UserRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil, UserRepositoryImpl userRepositoryImpl) {
        this.jwtUtil = jwtUtil;
    }

    // 토큰 유효성 검사 로직
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new AuthException("유효하지 않은 토큰");
        }

        String token = header.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new AuthException("유효하지 않은 토큰");
        }

        Long userId = jwtUtil.getUserId(token);

        if (userId == null) {
            throw new AuthException("사용자 정보 없음");
        }

        req.setAttribute("userId", String.valueOf(userId));
        return true;
    }
}
