package com.example.dance_community.dto.club;

import com.example.dance_community.entity.Club;

import java.time.LocalDateTime;

public record ClubResponse (
    Long clubId,
    String clubName,
    String description,
    Long memberCount,
    LocalDateTime createdAt
){
    public static ClubResponse from(Club club) {
        return new ClubResponse(
                club.getClubId(),
                club.getClubName(),
                club.getDescription(),
                (long) club.getMemberCount(),
                club.getCreatedAt()
        );
    }
}
