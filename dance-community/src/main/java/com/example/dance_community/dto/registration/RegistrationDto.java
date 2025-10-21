package com.example.dance_community.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class RegistrationDto {
    private Long eventId;
    private Long userId;

    private boolean isSuccess;

    private LocalDateTime createdAt;
}