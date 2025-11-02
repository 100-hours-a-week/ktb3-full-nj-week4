package com.example.dance_community.controller;

import com.example.dance_community.auth.GetUserId;
import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.post.PostCreateRequest;
import com.example.dance_community.dto.post.PostUpdateRequest;
import com.example.dance_community.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "3_Post", description = "게시글 관련 API")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "게시글을 새로 작성합니다.")
    @PostMapping()
    public ResponseEntity<ApiResponse<PostCreateRequest>> createPost(@GetUserId Long userId, @Valid @RequestBody PostUpdateRequest postUpdateRequest) {
        PostCreateRequest postCreateRequest = postService.createPost(userId, postUpdateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("게시글 생성 성공", postCreateRequest));
    }

    @Operation(summary = "게시글 조회", description = "게시글 id를 통해 정보를 불러옵니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostCreateRequest>> getPost(@PathVariable Long postId) {
        PostCreateRequest postCreateRequest = postService.getPost(postId);
        return ResponseEntity.ok(new ApiResponse<>("게시글 조회 성공", postCreateRequest));
    }

    @Operation(summary = "전체 게시글 조회", description = "전체 게시글의 정보를 불러옵니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostCreateRequest>>> getPosts() {
        List<PostCreateRequest> postCreateRequestList = postService.getPosts();
        return ResponseEntity.ok(new ApiResponse<>("게시글 전체 조회 성공", postCreateRequestList));
    }

    @Operation(summary = "내 게시글 수정", description = "사용자의 게시글을 수정합니다.")
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostCreateRequest>> updatePost(@PathVariable Long postId, @Valid @RequestBody PostUpdateRequest postUpdateRequest) {
        PostCreateRequest postCreateRequest = postService.updatePost(postId, postUpdateRequest);
        return ResponseEntity.ok(new ApiResponse<>("게시글 수정 성공", postCreateRequest));
    }

    @Operation(summary = "개시글 삭제", description = "게시글 id를 통해 정보를 삭제합니다.")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
