package com.example.dance_community.controller;

import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.clubJoin.ClubJoinCreateRequest;
import com.example.dance_community.dto.clubJoin.ClubJoinResponse;
import com.example.dance_community.security.UserDetail;
import com.example.dance_community.service.ClubJoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/club-joins")
@RequiredArgsConstructor
@Tag(name = "4_ClubJoin", description = "클럽 가입 관련 API")
@Slf4j
public class ClubJoinController {
    private final ClubJoinService clubJoinService;

    @Operation(summary = "클럽 가입", description = "클럽에 가입합니다.")
    @PostMapping()
    public ResponseEntity<ApiResponse<ClubJoinResponse>> createClubJoin(@AuthenticationPrincipal UserDetail userDetail, @Valid @RequestBody ClubJoinCreateRequest clubJoinCreateRequest) {
        ClubJoinResponse clubJoinResponse = clubJoinService.createClubJoin(userDetail.getUserId(), clubJoinCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("클럽 가입 성공", clubJoinResponse));
    }

    @Operation(summary = "사용자가 가입한 클럽 조회", description = "특정 사용자가 가입한 모든 클럽의 정보를 불러옵니다.")
    @GetMapping("/club")
    public ResponseEntity<ApiResponse<List<ClubJoinResponse>>> getUserClubs(@AuthenticationPrincipal UserDetail userDetail) {
        List<ClubJoinResponse> clubJoinResponses = clubJoinService.getUsersClubs(userDetail.getUserId());
        return ResponseEntity.ok(new ApiResponse<>("사용자가 가입한 클럽 조회 성공", clubJoinResponses));
    }

    @Operation(summary = "클럽에 가입한 사용자 조회", description = "특정 클럽에 가입한 모든 사용자의 정보를 불러옵니다.")
    @GetMapping("/user/{clubId}")
    public ResponseEntity<ApiResponse<List<ClubJoinResponse>>> getClubUsers(@PathVariable Long clubId) {
        List<ClubJoinResponse> clubJoinResponses = clubJoinService.getActiveUserInClub(clubId);
        return ResponseEntity.ok(new ApiResponse<>("클럽에 가입한 사용자 조회 성공", clubJoinResponses));
    }

    @Operation(summary = "클럽 가입 확인", description = "특정 사용자가 특정 클럽의 멤버인지 확인합니다.")
    @GetMapping("/check/{clubId}")
    public ResponseEntity<Boolean> checkMembership(@AuthenticationPrincipal UserDetail userDetail, @PathVariable Long clubId) {
        boolean isMember = clubJoinService.isClubJoin(userDetail.getUserId(), clubId);
        return ResponseEntity.ok(isMember);
    }

    @Operation(summary = "클럽 탈퇴", description = "클럽에서 탈퇴합니다.")
    @DeleteMapping("/{clubId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteClubJoin(@AuthenticationPrincipal UserDetail userDetaild, @PathVariable Long clubId) {
        clubJoinService.deleteClubJoin(userDetaild.getUserId(), clubId);
        return ResponseEntity.noContent().build();
    }
}