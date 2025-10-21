package com.example.dance_community.controller;

import com.example.dance_community.dto.auth.*;
import com.example.dance_community.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "1_Auth", description = "계정 관련 API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 사용자 이름을 입력 받아 회원가입합니다.")
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        AuthDto authDto = authService.signup(signupRequest);
        return ResponseEntity.ok(new AuthResponse("회원가입 성공", authDto));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력 받아 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login( @Valid @RequestBody LoginRequest loginRequest) {
        AuthDto authDto = authService.login(loginRequest);
        return ResponseEntity.ok(new AuthResponse("로그인 성공", authDto));
    }

    @Operation(summary = "토큰 재발급", description = "토큰이 만료됐을 때 재발급합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest request) {
        AuthDto authDto = authService.refreshAccessToken(Long.valueOf((String) request.getAttribute("userId")));
        return ResponseEntity.ok(new AuthResponse("토큰 재발급 성공", authDto));
    }
}