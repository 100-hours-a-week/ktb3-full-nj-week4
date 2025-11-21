package com.example.dance_community.dto.club;

import com.example.dance_community.entity.Club;

import java.time.LocalDateTime;
import java.util.List;

public record ClubResponse (
    Long clubId,
    String clubName,
    String intro,
    String clubImage,
    String locationName,
    String description,
    List<String> tags,
    Long memberCount,
    LocalDateTime createdAt
){
    public static ClubResponse from(Club club) {
        return new ClubResponse(
                club.getClubId(),
                club.getClubName(),
                club.getIntro(),
                club.getClubImage(),
                club.getLocationName(),
                club.getDescription(),
                club.getTags(),
                (long) club.getMemberCount(),
                club.getCreatedAt()
        );
    }
}
