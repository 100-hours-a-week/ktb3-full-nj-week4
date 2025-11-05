package com.example.dance_community.dto.clubJoin;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubJoinCreateRequest {
    @NotNull(message = "클럽 아이디 미입력")
    Long clubId;

    @NotBlank(message = "역할 미입력")
    String role;

    @NotBlank(message = "상태 미입력")
    String status;
}
