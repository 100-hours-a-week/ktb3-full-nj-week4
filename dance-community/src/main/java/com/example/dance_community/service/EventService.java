package com.example.dance_community.service;

import com.example.dance_community.dto.event.EventDto;
import com.example.dance_community.dto.event.EventRequest;
import com.example.dance_community.enums.EventType;
import com.example.dance_community.enums.Scope;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDto createEvent(Long userId, EventRequest eventRequest) {
        try {
            EventDto newEvent = EventDto.builder()
                    .userId(userId)
                    .scope(Scope.valueOf(eventRequest.getScope()))
                    .type(EventType.valueOf(eventRequest.getType()))
                    .clubId(eventRequest.getClubId())
                    .title(eventRequest.getTitle())
                    .content(eventRequest.getContent())
                    .tags(eventRequest.getTags())
                    .images(eventRequest.getImages())
                    .location(eventRequest.getLocation())
                    .capacity(eventRequest.getCapacity())
                    .startsAt(eventRequest.getStartsAt())
                    .endsAt(eventRequest.getEndsAt())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            return eventRepository.saveEvent(newEvent);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        } catch (Exception e) {
            throw new RuntimeException("행사 생성 실패");
        }
    }

    public EventDto getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));
    }

    public List<EventDto> getEvents() {
        try {
            return eventRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("행사 전체 조회 실패");
        }
    }

    public EventDto updateEvent(Long eventId, EventRequest eventRequest) {
        EventDto eventDto = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 조회 실패"));

        try {
            Scope newScope = eventRequest.getScope() != null ? Scope.valueOf(eventRequest.getScope()) : eventDto.getScope();
            EventType newType = eventRequest.getType() != null ? EventType.valueOf(eventRequest.getType()) : eventDto.getType();

            EventDto updatedEvent = eventDto.toBuilder()
                    .scope(newScope)
                    .type(newType)
                    .title(eventRequest.getTitle() != null ? eventRequest.getTitle() : eventDto.getTitle())
                    .content(eventRequest.getContent() != null ? eventRequest.getContent() : eventDto.getContent())
                    .tags(eventRequest.getTags() != null ? eventRequest.getTags() : eventDto.getTags())
                    .images(eventRequest.getImages() != null ? eventRequest.getImages() : eventDto.getImages())
                    .location(eventRequest.getLocation() != null ? eventRequest.getLocation() : eventDto.getLocation())
                    .capacity(eventRequest.getCapacity() != null ? eventRequest.getCapacity() : eventDto.getCapacity())
                    .startsAt(eventRequest.getStartsAt() != null ? eventRequest.getStartsAt() : eventDto.getStartsAt())
                    .endsAt(eventRequest.getEndsAt() != null ? eventRequest.getEndsAt() : eventDto.getEndsAt())
                    .updatedAt(LocalDateTime.now())
                    .build();

            return eventRepository.saveEvent(updatedEvent);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        } catch (Exception e) {
            throw new RuntimeException("행사 수정 실패");
        }
    }

    public void deleteEvent(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("행사 삭제 실패"));

        eventRepository.deleteById(eventId);
    }
}
