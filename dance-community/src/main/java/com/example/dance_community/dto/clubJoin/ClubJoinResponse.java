package com.example.dance_community.dto.clubJoin;

import com.example.dance_community.entity.ClubJoin;

public record ClubJoinResponse (
        Long clubId,
        Long userId,
        String role,
        String status
){
    public static ClubJoinResponse from(ClubJoin clubJoin) {
        return new ClubJoinResponse(
                clubJoin.getClub().getClubId(),
                clubJoin.getUser().getUserId(),
                clubJoin.getRole().name(),
                clubJoin.getStatus().name()
        );
    }
}
