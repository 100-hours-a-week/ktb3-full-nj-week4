package com.example.dance_community.dto.event;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Location {
    @NotBlank(message = "행사 장소명 미입력")
    private String name;
    @NotBlank(message = "행사 주소 미입력")
    private String address;
    private String link;
}
