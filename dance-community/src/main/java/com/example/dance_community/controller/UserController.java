package com.example.dance_community.controller;

import com.example.dance_community.auth.GetUserId;
import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.user.PasswordUpdateRequest;
import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.dto.user.UserUpdateRequest;
import com.example.dance_community.enums.ImageType;
import com.example.dance_community.security.CustomUserDetails;
import com.example.dance_community.service.FileStorageService;
import com.example.dance_community.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "2_User", description = "회원 관련 API")
public class UserController {
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "내 정보 조회", description = "사용자의 정보를 불러옵니다.")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse userResponse = userService.getUser(userDetails.getUserId());
        return ResponseEntity.ok(new ApiResponse<>("내 정보 조회 성공", userResponse));
    }

    @Operation(summary = "회원 정보 조회", description = "회원 id를 통해 정보를 불러옵니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUser(userId);
        return ResponseEntity.ok(new ApiResponse<>("회원 정보 조회 성공", userResponse));
    }

    @Operation(summary = "내 정보 수정", description = "사용자 정보를 수정합니다.")
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @GetUserId Long userId,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        String profileImagePath = fileStorageService.saveImage(profileImage, ImageType.PROFILE);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(nickname, profileImagePath);

        UserResponse userResponse = userService.updateUser(userId, userUpdateRequest);
        return ResponseEntity.ok(new ApiResponse<>("회원 정보 수정 성공", userResponse));
    }

    @Operation(summary = "내 비밀번호 수정", description = "사용자 비밀번호를 수정합니다.")
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<UserResponse>> updatePassword(
            @GetUserId Long userId, @Valid @RequestBody PasswordUpdateRequest request) {
        UserResponse userResponse = userService.updatePassword(userId, request);
        return ResponseEntity.ok(new ApiResponse<>("비밀번호 수정 성공", userResponse));
    }

    @Operation(summary = "탈퇴", description = "사용자 정보를 삭제합니다.")
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@GetUserId Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
