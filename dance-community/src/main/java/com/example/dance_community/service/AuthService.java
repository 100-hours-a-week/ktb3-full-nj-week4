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
        if (signupRequest == null
                || signupRequest.getEmail() == null || signupRequest.getEmail().isBlank()
                || signupRequest.getPassword() == null || signupRequest.getPassword().isBlank()
                || signupRequest.getUsername() == null || signupRequest.getUsername().isBlank()) {
            throw new InvalidRequestException("필수 필드가 누락되었습니다.");
        }

        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()){
            throw new ConflictException("이미 존재하는 이메일입니다.");
        }

        UserDto user = new UserDto();
        user.setEmail(signupRequest.getEmail());
        String hashedPW = BCrypt.hashpw(signupRequest.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPW);
        user.setUsername(signupRequest.getUsername());

        userRepository.saveUser(user);
        return new AuthDto(user.getUserId(), null, null);
    }

    public AuthDto login(LoginRequest loginRequest) {
        if (loginRequest == null
                || loginRequest.getEmail() == null || loginRequest.getEmail().isBlank()
                || loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            throw new InvalidRequestException("이메일과 비밀번호는 필수입니다.");
        }

        UserDto user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthException("등록된 이메일이 없습니다. 회원가입을 진행해주세요."));

        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

        return new AuthDto(user.getUserId(), accessToken, refreshToken);
    }

    public AuthDto refreshAccessToken(String nowRefreshToken) {
        if (nowRefreshToken == null || nowRefreshToken.isBlank()) {
            throw new InvalidRequestException("리프레시 토큰이 없습니다.");
        }

        if (!jwtUtil.validateToken(nowRefreshToken)) {
            throw new AuthException("유효하지 않은 리프레시 토큰입니다.");
        }

        if (!"refresh".equals(jwtUtil.getTokenType(nowRefreshToken))) {
            throw new AuthException("리프레시 토큰이 아닙니다.");
        }

        String userIdStr = jwtUtil.getUserId(nowRefreshToken);
        Long userId;
        try {
            userId = Long.valueOf(userIdStr);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("토큰에서 사용자 ID를 파싱할 수 없습니다.");
        }

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("해당 사용자가 존재하지 않습니다.");
        }

        String newAccessToken = jwtUtil.generateAccessToken(userId);

        return new AuthDto(userId, newAccessToken, nowRefreshToken);
    }
}
