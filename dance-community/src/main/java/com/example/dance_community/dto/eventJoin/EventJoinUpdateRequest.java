package com.example.dance_community.dto.eventJoin;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventJoinUpdateRequest {
    @NotBlank(message = "이벤트 아이디 미입력")
    Long eventId;

    @NotBlank(message = "상태 미입력")
    String status;
}
