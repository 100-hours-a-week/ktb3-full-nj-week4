package com.example.dance_community.dto.eventRegistration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventRegistrationRequest {
    @NotNull(message = "행사 ID 미입력")
    private Long eventId;

    @NotNull(message = "회원 ID 미입력")
    private Long userId;
}
