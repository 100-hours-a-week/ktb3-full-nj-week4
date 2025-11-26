package com.example.dance_community.service;

import com.example.dance_community.dto.club.ClubJoinResponse;
import com.example.dance_community.entity.Club;
import com.example.dance_community.entity.ClubJoin;
import com.example.dance_community.entity.User;
import com.example.dance_community.enums.ClubJoinStatus;
import com.example.dance_community.enums.ClubRole;
import com.example.dance_community.exception.ConflictException;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
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
    private final ClubAuthService clubAuthService;

    // 일반 사용자용
    @Transactional
    public ClubJoinResponse applyToClub(Long userId, Long clubId) {
        ClubJoin clubJoin = clubJoinRepository.findByUser_UserIdAndClub_ClubId(userId, clubId);
        if (clubJoin != null && (clubJoin.getStatus() == ClubJoinStatus.PENDING || clubJoin.getStatus() == ClubJoinStatus.ACTIVE)) {
            throw new ConflictException("이미 가입 신청한 클럽입니다.");
        }

        if(clubJoin != null && (clubJoin.getStatus() == ClubJoinStatus.CANCELED || clubJoin.getStatus() == ClubJoinStatus.LEFT)) {
            clubJoin.changeStatus(ClubJoinStatus.PENDING);
            return ClubJoinResponse.from(clubJoin);
        }

        User user = userService.findByUserId(userId);
        Club club = clubAuthService.findByClubId(clubId);

        ClubJoin newClubJoin = ClubJoin.builder()
                .user(user)
                .club(club)
                .role(ClubRole.MEMBER)
                .status(ClubJoinStatus.PENDING)
                .build();

        ClubJoin savedClubJoin = clubJoinRepository.save(newClubJoin);
        return ClubJoinResponse.from(savedClubJoin);
    }

    @Transactional
    public void cancelApplication(Long userId, Long clubId) {
        ClubJoin clubJoin = clubAuthService.findClubJoin(userId, clubId);

        if (clubJoin.getStatus() != ClubJoinStatus.PENDING) {
            throw new InvalidRequestException("신청 대기 중인 상태만 취소할 수 있습니다");
        }

        clubJoin.changeStatus(ClubJoinStatus.CANCELED);
    }

    @Transactional
    public void leaveClub(Long userId, Long clubId) {
        ClubJoin clubJoin = clubAuthService.findClubJoin(userId, clubId);

        if (clubJoin.getStatus() != ClubJoinStatus.ACTIVE) {
            throw new InvalidRequestException("활동 중인 멤버만 탈퇴할 수 있습니다");
        }

        if (clubJoin.getRole() == ClubRole.LEADER) {
            throw new InvalidRequestException("클럽 리더는 탈퇴할 수 없습니다. 리더 권한을 양도하세요.");
        }

        clubJoin.changeStatus(ClubJoinStatus.LEFT);
    }

    // 클럽 관리자용
    @Transactional
    public void approveApplication(Long managerId, Long clubId, Long applicantId) {
        clubAuthService.validateClubAuthority(managerId, clubId);

        ClubJoin clubJoin = clubAuthService.findClubJoin(applicantId, clubId);

        if (clubJoin.getStatus() != ClubJoinStatus.PENDING) {
            throw new InvalidRequestException("대기 중인 신청만 승인할 수 있습니다");
        }

        clubJoin.changeStatus(ClubJoinStatus.ACTIVE);
    }

    @Transactional
    public void rejectApplication(Long managerId, Long clubId, Long applicantId) {
        clubAuthService.validateClubAuthority(managerId, clubId);

        ClubJoin clubJoin = clubAuthService.findClubJoin(applicantId, clubId);

        if (clubJoin.getStatus() != ClubJoinStatus.PENDING) {
            throw new InvalidRequestException("대기 중인 신청만 거절할 수 있습니다");
        }

        clubJoin.changeStatus(ClubJoinStatus.REJECTED);
    }

    @Transactional
    public void kickMember(Long managerId, Long clubId, Long targetUserId) {
        clubAuthService.validateClubAuthority(managerId, clubId);

        ClubJoin clubJoin = clubAuthService.findClubJoin(targetUserId, clubId);

        if (clubJoin.getStatus() != ClubJoinStatus.ACTIVE) {
            throw new InvalidRequestException("활동 중인 멤버만 추방할 수 있습니다");
        }
        if (clubJoin.getRole() == ClubRole.LEADER) {
            throw new InvalidRequestException("클럽 리더는 추방할 수 없습니다");
        }
        if (managerId.equals(targetUserId)) {
            throw new InvalidRequestException("자기 자신을 추방할 수 없습니다");
        }

        clubJoin.changeStatus(ClubJoinStatus.LEFT);
    }

    // 조회용
    public ClubJoinResponse getJoinStatus(Long userId, Long clubId) {
        ClubJoin clubJoin = clubJoinRepository.findByUser_UserIdAndClub_ClubId(userId, clubId);

        if (clubJoin == null) {
            throw new NotFoundException("가입 정보를 찾을 수 없습니다");
        }

        return ClubJoinResponse.from(clubJoin);
    }

    public List<ClubJoinResponse> getUsersClubs(Long userId) {
        List<ClubJoin> clubJoins = clubJoinRepository.findByUser_UserIdAndStatus(userId, ClubJoinStatus.ACTIVE);
        return clubJoins.stream().map(ClubJoinResponse::from).toList();
    }

    public List<ClubJoinResponse> getActiveMembers(Long clubId) {
        List<ClubJoin> clubJoins = clubJoinRepository.findByClub_ClubIdAndStatus(clubId, ClubJoinStatus.ACTIVE);
        return clubJoins.stream().map(ClubJoinResponse::from).toList();
    }

    public List<ClubJoinResponse> getPendingApplications(Long managerId, Long clubId) {
        clubAuthService.validateClubAuthority(managerId, clubId);
        List<ClubJoin> pendingJoins = clubJoinRepository.findByClub_ClubIdAndStatus(clubId, ClubJoinStatus.PENDING);
        return pendingJoins.stream().map(ClubJoinResponse::from).toList();
    }

    public boolean isClubJoin(Long userId, Long clubId) {
        return clubJoinRepository.existsByUser_UserIdAndClub_ClubId(userId, clubId);
    }
}