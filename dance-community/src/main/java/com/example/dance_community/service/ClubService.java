package com.example.dance_community.service;

import com.example.dance_community.dto.club.ClubCreateRequest;
import com.example.dance_community.dto.club.ClubResponse;
import com.example.dance_community.dto.club.ClubUpdateRequest;
import com.example.dance_community.entity.Club;
import com.example.dance_community.entity.User;
import com.example.dance_community.enums.ClubJoinStatus;
import com.example.dance_community.enums.ClubRole;
import com.example.dance_community.repository.ClubRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final UserService userService;
    private final ClubAuthService clubAuthService;
    private final FileStorageService fileStorageService;
    private final EntityManager em;

    @Transactional
    public ClubResponse createClub(Long userId, ClubCreateRequest request) {
        User user = userService.findByUserId(userId);

        Club club = Club.builder()
                .clubName(request.getClubName())
                .intro(request.getIntro())
                .description(request.getDescription())
                .locationName(request.getLocationName())
                .clubType(request.getClubType())
                .clubImage(request.getClubImage())
                .tags(request.getTags())
                .build();

        club.addMember(user, ClubRole.LEADER, ClubJoinStatus.ACTIVE);
        Club newClub = clubRepository.save(club);
        return ClubResponse.from(newClub);
    }

    public ClubResponse getClub(Long clubId) {
        Club club = clubAuthService.findByClubId(clubId);
        return ClubResponse.from(club);
    }

    public List<ClubResponse> getClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map(ClubResponse::from).toList();
    }

    @Transactional
    public ClubResponse updateClub(Long userId, Long clubId, ClubUpdateRequest request) {
        clubAuthService.validateClubAuthority(userId, clubId);
        Club club = clubAuthService.findByClubId(clubId);

        club.updateClub(
                request.getClubName(),
                request.getIntro(),
                request.getDescription(),
                request.getLocationName(),
                request.getClubType(),
                request.getClubImage(),
                request.getTags()
        );

        Club savedClub = clubRepository.save(club);
        return ClubResponse.from(savedClub);
    }

    @Transactional
    public ClubResponse deleteClubImage(Long userId, Long clubId) {
        clubAuthService.validateClubAuthority(userId, clubId);
        Club club = clubAuthService.findByClubId(clubId);

        if (club.getClubImage() != null) {
            fileStorageService.deleteFile(club.getClubImage());
            club.deleteImage();
        }

        Club savedClub = clubRepository.save(club);
        return ClubResponse.from(savedClub);
    }

    @Transactional
    public void deleteClub(Long userId, Long clubId) {
        clubAuthService.validateLeaderAuthority(userId, clubId);
        Club club = clubAuthService.findByClubId(clubId);
        User user = userService.findByUserId(userId);

        if (club.getClubImage() != null) {
            fileStorageService.deleteFile(club.getClubImage());
        }

        club.removeMember(user);
        club.delete();

        em.flush();
        em.clear();
    }
}