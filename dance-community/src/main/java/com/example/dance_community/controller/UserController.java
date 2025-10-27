package com.example.dance_community.controller;

import com.example.dance_community.auth.GetUserId;
import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.user.UserCreateRequest;
import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.dto.user.UserUpdateRequest;
import com.example.dance_community.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "2_User", description = "회원 관련 API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "내 정보 조회", description = "사용자의 정보를 불러옵니다.")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(@GetUserId Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse<>("내 정보 조회 성공", userResponse));
    }

    @Operation(summary = "회원 정보 조회", description = "회원 id를 통해 정보를 불러옵니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse<>("회원 정보 조회 성공", userResponse));
    }

    @Operation(summary = "내 정보 수정", description = "사용자 정보를 수정합니다.")
    @PatchMapping()
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@GetUserId Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse userResponse = userService.updateUser(userId, userUpdateRequest);
        return ResponseEntity.ok(new ApiResponse<>("회원 정보 조회 수정", userResponse));
    }

    @Operation(summary = "탈퇴", description = "사용자 정보를 삭제합니다.")
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteUser(@GetUserId Long userId) {
        userService.deleteCurrentUser(userId);
        return ResponseEntity.noContent().build();
    }
}
