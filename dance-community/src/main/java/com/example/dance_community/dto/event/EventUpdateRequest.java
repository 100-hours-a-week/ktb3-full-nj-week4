package com.example.dance_community.dto.event;

import com.example.dance_community.entity.Event;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record EventUpdateRequest(
        // Details
        @NotBlank(message = "행사 제목 미입력")
        String title,
        @NotBlank(message = "행사 내용 미입력")
        String content,
        List<String> tags,
        List<String> images,

        // Location
        @NotBlank(message = "행사 장소명 미입력")
        String locationName,
        @NotBlank(message = "행사 주소 미입력")
        String locationAddress,
        String locationLink,

        // Capacity
        @NotNull(message = "행사 마감인원 미입력")
        Long capacity,
        Long currentParticipants,

        // Schedule
        @NotBlank(message = "행사 시작시간 미입력")
        LocalDateTime startsAt,
        @NotBlank(message = "행사 종료시간 미입력")
        LocalDateTime endsAt
){
    public Event to(Event existingEvent) {
        return existingEvent.toBuilder()
                .title(this.title)
                .content(this.content)
                .tags(this.tags)
                .images(this.images)
                .locationName(this.locationName)
                .locationAddress(this.locationAddress)
                .locationLink(this.locationLink)
                .capacity(this.capacity)
                .currentParticipants(this.currentParticipants)
                .startsAt(this.startsAt)
                .endsAt(this.endsAt)
                .build();
    }
}
