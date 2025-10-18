package com.example.dance_community.controller;

import com.example.dance_community.dto.user.UserDto;
import com.example.dance_community.dto.user.UserResponse;
import com.example.dance_community.dto.user.UserRequest;
import com.example.dance_community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        UserDto userDto = userService.getUserById(Long.valueOf(userId));
        return ResponseEntity.ok(new UserResponse("회원 정보 조회 성공", userDto));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserRequest request) {
        UserDto userDto = userService.updateUser(Long.valueOf(userId), request);
        return ResponseEntity.ok(new UserResponse("회원 정보 수정 성공", userDto));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        userService.deleteCurrentUser(Long.valueOf(userId));
    }
}
