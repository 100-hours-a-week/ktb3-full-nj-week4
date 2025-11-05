package com.example.dance_community.service;

import com.example.dance_community.dto.club.ClubCreateRequest;
import com.example.dance_community.dto.club.ClubResponse;
import com.example.dance_community.dto.club.ClubUpdateRequest;
import com.example.dance_community.entity.Club;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.jpa.ClubRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;

    @Transactional
    public ClubResponse createClub(ClubCreateRequest request) {
        Club club = Club.builder()
                .clubName(request.getClubName())
                .description(request.getDescription())
                .build();

        Club newClub = clubRepository.save(club);
        return ClubResponse.from(newClub);
    }

    public ClubResponse getClub(Long clubId) {
        Club club = getActiveClub(clubId);
        return ClubResponse.from(club);
    }

    public List<ClubResponse> getClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map(ClubResponse::from).toList();
    }

    @Transactional
    public ClubResponse updateClub(Long clubId, ClubUpdateRequest request) {
        Club club = getActiveClub(clubId);

        club.updateClub(
            request.getClubName(),
            request.getDescription()
        );
        return ClubResponse.from(club);
    }

    @Transactional
    public void deleteClub(Long clubId) {
        Club club = getActiveClub(clubId);
        club.delete();
    }

    Club getActiveClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new NotFoundException("클럽을 찾을 수 없습니다"));
    }
}