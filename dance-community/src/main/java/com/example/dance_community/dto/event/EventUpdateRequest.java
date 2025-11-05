package com.example.dance_community.dto.event;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class EventUpdateRequest{
    // 행사 관련 내용 (제목, 내용, 태그, 이미지)
    @NotBlank(message = "행사 제목 미입력")
    String title;
    @NotBlank(message = "행사 내용 미입력")
    String content;
    List<String> tags;
    List<String> images;

    // 행사 장소 정보 (이름, 주소, 링크)
    String locationName;
    String locationAddress;
    String locationLink;

    // 행사 총 수용 인원
    @NotNull(message = "행사 마감인원 미입력")
    Long capacity;

    // 행사 일시 (시작, 종료 시간)
    @NotBlank(message = "행사 시작시간 미입력")
    LocalDateTime startsAt;
    @NotBlank(message = "행사 종료시간 미입력")
    LocalDateTime endsAt;
}
