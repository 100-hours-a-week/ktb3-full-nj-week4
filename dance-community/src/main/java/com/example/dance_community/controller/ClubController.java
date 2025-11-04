package com.example.dance_community.controller;

import com.example.dance_community.auth.GetUserId;
import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.club.ClubCreateRequest;
import com.example.dance_community.dto.club.ClubResponse;
import com.example.dance_community.dto.club.ClubUpdateRequest;
import com.example.dance_community.service.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
@Tag(name = "3_Club", description = "클럽 관련 API")
public class ClubController {
    private final ClubService clubService;

    @Operation(summary = "클럽 생성", description = "클럽을 새로 작성합니다.")
    @PostMapping()
    public ResponseEntity<ApiResponse<ClubResponse>> createClub(@GetUserId Long userId, @Valid @RequestBody ClubCreateRequest clubCreateRequest) {
        ClubResponse clubResponse = clubService.createClub(clubCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("클럽 생성 성공", clubResponse));
    }

    @Operation(summary = "클럽 조회", description = "클럽 id를 통해 정보를 불러옵니다.")
    @GetMapping("/{clubId}")
    public ResponseEntity<ApiResponse<ClubResponse>> getClub(@PathVariable Long clubId) {
        ClubResponse clubResponse = clubService.getClub(clubId);
        return ResponseEntity.ok(new ApiResponse<>("클럽 조회 성공", clubResponse));
    }

    @Operation(summary = "내 클럽 조회", description = "사용자의 클럽 정보를 불러옵니다.")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<ClubResponse>> getMyClub(@GetUserId Long userId) {
        ClubResponse clubResponse = clubService.getClub(userId);
        return ResponseEntity.ok(new ApiResponse<>("내 클럽 조회 성공", clubResponse));
    }

    @Operation(summary = "전체 클럽 조회", description = "전체 클럽의 정보를 불러옵니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClubResponse>>> getClubs() {
        List<ClubResponse> clubResponses = clubService.getClubs();
        return ResponseEntity.ok(new ApiResponse<>("클럽 전체 조회 성공", clubResponses));
    }

    @Operation(summary = "내 클럽 수정", description = "사용자의 클럽을 수정합니다.")
    @PatchMapping("/{clubId}")
    public ResponseEntity<ApiResponse<ClubResponse>> updateClub(@PathVariable Long clubId, @Valid @RequestBody ClubUpdateRequest clubUpdateRequest) {
        ClubResponse clubResponse = clubService.updateClub(clubId, clubUpdateRequest);
        return ResponseEntity.ok(new ApiResponse<>("클럽 수정 성공", clubResponse));
    }


    @Operation(summary = "클럽 삭제", description = "클럽 id를 통해 정보를 삭제합니다.")
    @DeleteMapping("/{clubId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteClub(@PathVariable Long clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.noContent().build();
    }
}
