package com.example.dance_community.service;

import com.example.dance_community.dto.clubJoin.ClubJoinCreateRequest;
import com.example.dance_community.dto.clubJoin.ClubJoinResponse;
import com.example.dance_community.entity.Club;
import com.example.dance_community.entity.ClubJoin;
import com.example.dance_community.entity.User;
import com.example.dance_community.entity.enums.ClubJoinStatus;
import com.example.dance_community.entity.enums.ClubRole;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.repository.jpa.ClubJoinRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubJoinService {
    private final ClubJoinRepository clubJoinRepository;
    private final UserService userService;
    private final ClubService clubService;

    @Transactional
    public ClubJoinResponse createClubJoin(Long userId, ClubJoinCreateRequest request) {
        User user = userService.getActiveUser(userId);
        Club club = clubService.getActiveClub(request.getClubId());

        if (clubJoinRepository.existsByClubAndUser(user, club)) {
            throw new ConflictException("이미 가입한 클럽");
        }

        ClubJoin newClubJoin = ClubJoin.builder()
                .user(user)
                .club(club)
                .role(ClubRole.valueOf(request.getRole()))
                .status(ClubJoinStatus.valueOf(request.getStatus()))
                .build();

        ClubJoin savedClubJoin = clubJoinRepository.save(newClubJoin);
        return ClubJoinResponse.from(savedClubJoin);
    }

    public List<ClubJoinResponse> getUserClubs(Long userId) {
        User user = userService.getActiveUser(userId);

        List<ClubJoin> clubJoins = clubJoinRepository.findByUser(user);
        return clubJoins.stream()
                .map(ClubJoinResponse::from)
                .toList();
    }

    public List<ClubJoinResponse> getClubUsers(Long clubId) {
        Club club = clubService.getActiveClub(clubId);

        List<ClubJoin> clubJoins = clubJoinRepository.findByClub(club);
        return clubJoins.stream()
                .map(ClubJoinResponse::from)
                .toList();
    }

    @Transactional
    public void deleteClubJoin(Long userId, Long clubId) {
        User user = userService.getActiveUser(userId);
        Club club = clubService.getActiveClub(clubId);

        if (!clubJoinRepository.existsByClubAndUser(user, club)) {
            throw new InvalidRequestException("가입하지 않은 클럽");
        }

        clubJoinRepository.deleteByClubAndUser(user, club);
    }

    public boolean isClubJoin(Long userId, Long clubId) {
        User user = userService.getActiveUser(userId);
        Club club = clubService.getActiveClub(clubId);

        return clubJoinRepository.existsByClubAndUser(user, club);
    }
}
