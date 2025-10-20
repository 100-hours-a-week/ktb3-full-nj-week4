package com.example.dance_community.controller;

import com.example.dance_community.dto.auth.*;
import com.example.dance_community.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        AuthDto authDto = authService.signup(signupRequest);
        return ResponseEntity.ok(new AuthResponse("회원가입 성공", authDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login( @Valid @RequestBody LoginRequest loginRequest) {
        AuthDto authDto = authService.login(loginRequest);
        return ResponseEntity.ok(new AuthResponse("로그인 성공", authDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam String refreshToken) {
        AuthDto authDto = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(new AuthResponse("토큰 재발급 성공", authDto));
    }
}
