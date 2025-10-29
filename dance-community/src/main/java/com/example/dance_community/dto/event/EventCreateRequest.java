package com.example.dance_community.dto.event;

import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
import com.example.dance_community.validation.ValidScopeTypeEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@ValidScopeTypeEvent
public record EventCreateRequest(
        // Classification(수정 불가)
        @NotBlank(message = "행사 범위 미입력")
        String scope,
        Long clubId,
        @NotBlank(message = "행사 종류 미입력")
        String type,

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
    public com.example.dance_community.entity.Event to() {
        return com.example.dance_community.entity.Event.builder()
                .scope(Scope.valueOf(this.scope))
                .clubId(this.clubId)
                .type(EventType.valueOf(this.type))
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