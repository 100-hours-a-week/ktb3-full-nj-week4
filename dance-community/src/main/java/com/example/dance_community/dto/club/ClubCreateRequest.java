package com.example.dance_community.dto.club;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClubCreateRequest {
    @NotBlank(message = "클럽 이름 미입력")
    private String clubName;

    @NotNull(message = "게시물 내용 미입력")
    private String description;
}
