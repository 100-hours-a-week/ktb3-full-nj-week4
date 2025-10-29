package com.example.dance_community.service;

import com.example.dance_community.dto.event.EventCreateRequest;
import com.example.dance_community.dto.event.EventResponse;
import com.example.dance_community.dto.event.EventUpdateRequest;
import com.example.dance_community.entity.Event;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepo eventRepo;

    @Autowired
    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    private Event checkEventExists(Long eventId) {
        return eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));
    }

    public EventResponse createEvent(Long userId, EventCreateRequest eventCreateRequest) {
        try {
            Event newEvent = eventCreateRequest.to();
            newEvent = newEvent.toBuilder()
                    .userId(userId)
                    .currentParticipants(0L)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            Event savedEvent = eventRepo.saveEvent(newEvent);
            return EventResponse.from(savedEvent);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        } catch (Exception e) {
            throw new RuntimeException("행사 생성 실패");
        }
    }

    public EventResponse getEvent(Long eventId) {
        Event event = this.checkEventExists(eventId);
        return EventResponse.from(event);
    }

    public List<EventResponse> getEvents() {
        try {
            // 구현 이유 : 코드 유연성과 재사용성 / 나중에 일부 필드만 담은 dto만 필요할 때 유연한 구조
            List<Event> events = eventRepo.findAll();
            return events.stream().map((event)->EventResponse.from(event)).toList();
        } catch (Exception e) {
            throw new RuntimeException("행사 전체 조회 실패");
        }
    }

    public EventResponse updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) {
        Event event = this.checkEventExists(eventId);

        try {
            event.updateDetails(eventUpdateRequest.title(), eventUpdateRequest.content(),
                    eventUpdateRequest.tags(), eventUpdateRequest.images());
            event.updateLocation(eventUpdateRequest.locationName(), eventUpdateRequest.locationAddress(),
                    eventUpdateRequest.locationLink());
            event.updateCapacity(eventUpdateRequest.capacity());
            event.updateSchedule(eventUpdateRequest.startsAt(), eventUpdateRequest.endsAt());
            event.updateTime();

            return EventResponse.from(event);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        } catch (Exception e) {
            throw new RuntimeException("행사 수정 실패");
        }
    }

    public void deleteEvent(Long eventId) {
        this.checkEventExists(eventId);
        eventRepo.deleteById(eventId);
    }

    public void validateCanRegister(Long eventId) {
        Event event = this.checkEventExists(eventId);

        if (event.getCapacity() != null &&
                event.getCurrentParticipants() >= event.getCapacity()) {
            throw new ConflictException("행사 정원 초과");
        }

        // TODO: 추가 검증 추가(행사 시작 전 파악/취소 행사 파악 등)
    }

    public void incrementParticipants(Long eventId) {
        Event event = this.checkEventExists(eventId);

        event.incrementParticipants();
        eventRepo.saveEvent(event);
    }

    public void decrementParticipants(Long eventId) {
        Event event = this.checkEventExists(eventId);

        event.decrementParticipants();
        eventRepo.saveEvent(event);
    }
}
