package com.example.dance_community.dto.club;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClubCreateRequest {
    @NotBlank(message = "클럽 이름 미입력")
    private String clubName;

    @NotBlank(message = "클럽 한 줄 소개 미입력")
    private String intro;

    @NotBlank(message = "클럽 위치 미입력")
    private String locationName;

    @NotBlank(message = "클럽 설명 미입력")
    private String description;

    private String clubImage;
    private List<String> tags;
}
