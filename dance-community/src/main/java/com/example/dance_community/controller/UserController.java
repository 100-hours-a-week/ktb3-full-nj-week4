package com.example.dance_community.controller;

import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.user.UserDto;
import com.example.dance_community.dto.user.UserRequest;
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
    public ResponseEntity<ApiResponse<UserDto>> getUser(HttpServletRequest request) {
        UserDto userDto = userService.getUserById(Long.valueOf((String) request.getAttribute("userId")));
        return ResponseEntity.ok(new ApiResponse<>("내 정보 조회 성공", userDto));
    }

    @Operation(summary = "회원 정보 조회", description = "회원 id를 통해 정보를 불러옵니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse<>("회원 정보 조회 성공", userDto));
    }

    @Operation(summary = "내 정보 수정", description = "사용자 정보를 수정합니다.")
    @PatchMapping()
    public ResponseEntity<ApiResponse<UserDto>> updateUser(HttpServletRequest request, @RequestBody UserRequest userRequest) {
        UserDto userDto = userService.updateUser(Long.valueOf((String) request.getAttribute("userId")), userRequest);
        return ResponseEntity.ok(new ApiResponse<>("회원 정보 조회 수정", userDto));
    }

    @Operation(summary = "탈퇴", description = "사용자 정보를 삭제합니다.")
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteUser(HttpServletRequest request) {
        userService.deleteCurrentUser(Long.valueOf((String) request.getAttribute("userId")));
        return ResponseEntity.noContent().build();
    }
}
