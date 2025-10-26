package com.example.dance_community.service;

import com.example.dance_community.dto.event.EventDto;
import com.example.dance_community.dto.event.EventUpdateRequest;
import com.example.dance_community.entity.Event;
import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
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

    public EventDto createEvent(Long userId, EventUpdateRequest eventUpdateRequest) {
        try {
            Event newEvent = Event.builder()
                    .userId(userId)
                    .scope(Scope.valueOf(eventUpdateRequest.getScope()))
                    .type(EventType.valueOf(eventUpdateRequest.getType()))
                    .clubId(eventUpdateRequest.getClubId())
                    .title(eventUpdateRequest.getTitle())
                    .content(eventUpdateRequest.getContent())
                    .tags(eventUpdateRequest.getTags())
                    .images(eventUpdateRequest.getImages())
                    .location(eventUpdateRequest.getLocation())
                    .capacity(eventUpdateRequest.getCapacity())
                    .startsAt(eventUpdateRequest.getStartsAt())
                    .endsAt(eventUpdateRequest.getEndsAt())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Event savedEvent = eventRepo.saveEvent(newEvent);
            return savedEvent.toDto();
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        } catch (Exception e) {
            throw new RuntimeException("행사 생성 실패");
        }
    }

    public EventDto getEvent(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));
        return event.toDto();
    }

    public List<EventDto> getEvents() {
        try {
            // 구현 이유 : 코드 유연성과 재사용성 / 나중에 일부 필드만 담은 dto만 필요할 때 유연한 구조
            List<Event> events = eventRepo.findAll();
            return events.stream().map(Event::toDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("행사 전체 조회 실패");
        }
    }

    public EventDto updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));

        try {
            if (eventUpdateRequest.getScope() != null) {
                event.setScope(Scope.valueOf(eventUpdateRequest.getScope()));
            }
            if (eventUpdateRequest.getType() != null) {
                event.setType(EventType.valueOf(eventUpdateRequest.getType()));
            }
            if (eventUpdateRequest.getTitle() != null) {
                event.setTitle(eventUpdateRequest.getTitle());
            }
            if (eventUpdateRequest.getContent() != null) {
                event.setContent(eventUpdateRequest.getContent());
            }
            if (eventUpdateRequest.getTags() != null) {
                event.setTags(eventUpdateRequest.getTags());
            }
            if (eventUpdateRequest.getImages() != null) {
                event.setImages(eventUpdateRequest.getImages());
            }
            if (eventUpdateRequest.getLocation() != null) {
                event.setLocation(eventUpdateRequest.getLocation());
            }
            if (eventUpdateRequest.getCapacity() != null) {
                event.setCapacity(eventUpdateRequest.getCapacity());
            }
            if (eventUpdateRequest.getStartsAt() != null) {
                event.setStartsAt(eventUpdateRequest.getStartsAt());
            }
            if (eventUpdateRequest.getEndsAt() != null) {
                event.setEndsAt(eventUpdateRequest.getEndsAt());
            }
            event.setUpdatedAt(LocalDateTime.now());

            return eventRepo.saveEvent(event).toDto();
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        } catch (Exception e) {
            throw new RuntimeException("행사 수정 실패");
        }
    }

    public void deleteEvent(Long eventId) {
        eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 삭제 실패"));

        eventRepo.deleteById(eventId);
    }
}
