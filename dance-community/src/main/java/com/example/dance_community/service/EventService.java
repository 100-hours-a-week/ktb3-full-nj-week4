package com.example.dance_community.service;

import com.example.dance_community.dto.event.EventCreateRequest;
import com.example.dance_community.dto.event.EventResponse;
import com.example.dance_community.dto.event.EventUpdateRequest;
import com.example.dance_community.entity.Club;
import com.example.dance_community.entity.Event;
import com.example.dance_community.entity.User;
import com.example.dance_community.entity.enums.Scope;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.in_memory.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepo eventRepo;
    private final UserService userService;
    private final ClubService ClubService;

    public EventResponse createEvent(Long userId, EventCreateRequest request) {
        User host = userService.getActiveUser(userId);

        Club club = null;
        if (Scope.CLUB.toString().equals(request.getScope())) {
            Long clubId = request.getClubId();
            if (clubId == null) {
                throw new InvalidRequestException("공개 범위가 CLUB일 경우 clubId가 필요");
            }
            club = ClubService.getActiveClub(clubId);
        }

        Event event = Event.builder()
                .host(host)
                .scope(Scope.valueOf(request.getScope().toUpperCase()))
                .club(club)
                .title(request.getTitle())
                .content(request.getContent())
                .tags(request.getTags())
                .images(request.getImages())
                .locationName(request.getLocationName())
                .locationAddress(request.getLocationAddress())
                .locationLink(request.getLocationLink())
                .capacity(request.getCapacity())
                .startsAt(request.getStartsAt())
                .endsAt(request.getEndsAt())
                .build();

        Event newEvent = eventRepo.saveEvent(event);
        return EventResponse.from(newEvent);
    }

    public EventResponse getEvent(Long eventId) {
        Event event = this.getActiveEvent(eventId);
        return EventResponse.from(event);
    }

    public List<EventResponse> getEvents() {
        // 구현 이유 : 코드 유연성과 재사용성 / 나중에 일부 필드만 담은 dto만 필요할 때 유연한 구조
        List<Event> events = eventRepo.findAll();
        return events.stream().map(EventResponse::from).toList();
    }

    public EventResponse updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) {
        Event event = this.getActiveEvent(eventId);

        event.updateEvent(
                eventUpdateRequest.getTitle(),
                eventUpdateRequest.getContent(),
                eventUpdateRequest.getTags(),
                eventUpdateRequest.getImages(),
                eventUpdateRequest.getLocationName(),
                eventUpdateRequest.getLocationAddress(),
                eventUpdateRequest.getLocationLink(),
                eventUpdateRequest.getCapacity(),
                eventUpdateRequest.getStartsAt(),
                eventUpdateRequest.getEndsAt()
        );

        return EventResponse.from(event);
    }

    public void deleteEvent(Long eventId) {
        this.getActiveEvent(eventId);
        eventRepo.deleteById(eventId);
    }

    Event getActiveEvent(Long eventId) {
        return eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));
    }
}
