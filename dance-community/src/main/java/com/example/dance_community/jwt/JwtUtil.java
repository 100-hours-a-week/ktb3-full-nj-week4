package com.example.dance_community.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // JWT : Header-Payload-Signature로 구성

    // JWT 서명 시 사용되는 비밀키 배정
    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    private final long accessTokenExpirationMs = 1000L * 60 * 60;       // 액세스 토큰 유효기간 : 1시간
    private final long refreshTokenExpirationMs = 1000L * 60 * 60 * 24 * 7; // 리프레시 토큰 유효기간 : 7일

    public String generateAccessToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) //sub : 토큰 주체
                .setIssuedAt(new Date()) // iat : 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs)) // exp : 만료 시간
                .signWith(key) //서명
                .compact(); //문자열 반환
    }

    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(key)
                .compact();
    }

    // 토큰 유효성 검증(서명 유효성 & 토큰 만료 여부 확인)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 토큰 내부 Payload 정보 가져오기 -> userId 받아오기
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserId(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }
}

