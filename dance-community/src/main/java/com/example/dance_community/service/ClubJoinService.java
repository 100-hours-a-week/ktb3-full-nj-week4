package com.example.dance_community.service;

import com.example.dance_community.dto.clubJoin.ClubJoinCreateRequest;
import com.example.dance_community.dto.clubJoin.ClubJoinResponse;
import com.example.dance_community.entity.Club;
import com.example.dance_community.entity.ClubJoin;
import com.example.dance_community.entity.User;
import com.example.dance_community.enums.ClubJoinStatus;
import com.example.dance_community.enums.ClubRole;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.repository.ClubJoinRepository;
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
        Long clubId = request.getClubId();

        if (isClubJoin(userId, clubId)) {
            throw new ConflictException("이미 가입한 클럽");
        }

        User user = userService.getActiveUser(userId);
        Club club = clubService.getActiveClub(clubId);

        ClubJoin newClubJoin = ClubJoin.builder()
                .user(user)
                .club(club)
                .role(ClubRole.valueOf(request.getRole()))
                .status(ClubJoinStatus.valueOf(request.getStatus()))
                .build();

        ClubJoin savedClubJoin = clubJoinRepository.save(newClubJoin);
        return ClubJoinResponse.from(savedClubJoin);
    }

    public List<ClubJoinResponse> getUsersClubs(Long userId) {
        List<ClubJoin> clubJoins = clubJoinRepository.findByUser_UserIdAndStatus(userId, ClubJoinStatus.valueOf("ACTIVE"));
        return clubJoins.stream()
                .map(ClubJoinResponse::from)
                .toList();
    }

    public List<ClubJoinResponse> getActiveUserInClub(Long clubId) {
        List<ClubJoin> clubJoins = clubJoinRepository.findByClub_ClubIdAndStatus(clubId, ClubJoinStatus.valueOf("ACTIVE"));
        return clubJoins.stream()
                .map(ClubJoinResponse::from)
                .toList();
    }

    @Transactional
    public void deleteClubJoin(Long userId, Long clubId) {
        if (!isClubJoin(userId, clubId)) {
            throw new InvalidRequestException("가입하지 않은 클럽");
        }

        ClubJoin clubJoin = clubJoinRepository.findByUser_UserIdAndClub_ClubId(userId, clubId);
        clubJoin.changeStatus(ClubJoinStatus.valueOf("REJECTED"));
    }

    public boolean isClubJoin(Long userId, Long clubId) {
        return clubJoinRepository.existsByUser_UserIdAndClub_ClubId(userId, clubId);
    }

    public void validateClubAuthority(Long userId, Long clubId) {
        ClubJoin clubJoin = clubJoinRepository.findByUser_UserIdAndClub_ClubId(userId, clubId);
        if (clubJoin == null || !clubJoin.hasManagementPermission()) {
            throw new InvalidRequestException("클럽 권한이 없습니다");
        }
    }
}