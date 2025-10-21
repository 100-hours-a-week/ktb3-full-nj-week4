package com.example.dance_community.controller;

import com.example.dance_community.dto.user.UserDto;
import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.dto.user.UserRequest;
import com.example.dance_community.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(HttpServletRequest request) {
        UserDto userDto = userService.getUserById(Long.valueOf((String) request.getAttribute("userId")));
        return ResponseEntity.ok(new UserResponse("내 정보 조회 성공", userDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(new UserResponse("회원 정보 조회 성공", userDto));
    }

    @PatchMapping()
    public ResponseEntity<UserResponse> updateUser(HttpServletRequest request, @RequestBody UserRequest userRequest) {
        UserDto userDto = userService.updateUser(Long.valueOf((String) request.getAttribute("userId")), userRequest);
        return ResponseEntity.ok(new UserResponse("회원 정보 수정 성공", userDto));
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(HttpServletRequest request) {
        userService.deleteCurrentUser(Long.valueOf((String) request.getAttribute("userId")));
    }
}
