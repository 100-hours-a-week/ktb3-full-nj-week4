package com.example.dance_community.jwt;

import com.example.dance_community.exception.AuthException;
import com.example.dance_community.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;

    public JwtInterceptor(JwtUtil jwtUtil, UserRepo userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    // 토큰 유효성 검사 로직
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new AuthException("토큰 없음");
        }

        String token = header.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new AuthException("유효하지 않은 토큰");
        }

        Long userId = jwtUtil.getUserId(token);

        if (userId == null || !userRepo.existsById(userId)) {
            throw new AuthException("사용자 인증 실패");
        }

        req.setAttribute("userId", userId);
        return true;
    }
}
