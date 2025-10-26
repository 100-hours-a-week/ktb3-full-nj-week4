package com.example.dance_community.dto.event;

import com.example.dance_community.entity.Event;
import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
import com.example.dance_community.validation.ValidScopeTypeEvent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@ValidScopeTypeEvent
public class EventUpdateRequest {
    @NotBlank(message = "행사 범위 미입력")
    private String scope;

    @NotBlank(message = "행사 종류 미입력")
    private String type;

    @NotBlank(message = "행사 제목 미입력")
    private String title;

    @NotBlank(message = "행사 내용 미입력")
    private String content;

    @NotNull(message = "행사 내용 미입력")
    private List<String> tags;

    private List<String> images;

    @Valid
    private Location location;

    private Long clubId;
    @NotNull(message = "행사 마감인원 미입력")
    private Long capacity;

    @NotNull(message = "행사 시작시간 미입력")
    private LocalDateTime startsAt;
    @NotNull(message = "행사 종료시간 미입력")
    private LocalDateTime endsAt;

    public Event toEntity(Long userId) {
        return Event.builder()
                .userId(userId)
                .scope(Scope.valueOf(this.scope))
                .type(EventType.valueOf(this.type))
                .clubId(this.clubId)
                .title(this.title)
                .content(this.content)
                .tags(this.tags)
                .images(this.images)
                .location(this.location)
                .capacity(this.capacity)
                .currentParticipants(0L)
                .startsAt(this.startsAt)
                .endsAt(this.endsAt)
                .build();
    }
}
