package com.example.dance_community.controller;

import com.example.dance_community.auth.GetUserId;
import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.post.PostCreateRequest;
import com.example.dance_community.dto.post.PostResponse;
import com.example.dance_community.dto.post.PostUpdateRequest;
import com.example.dance_community.service.FileStorageService;
import com.example.dance_community.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "5_Post", description = "게시물 관련 API")
public class PostController {
    private final PostService postService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "게시물 생성", description = "게시물을 새로 작성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @GetUserId Long userId,
            @RequestParam("scope") String scope,
            @RequestParam(value = "clubId", required = false) Long clubId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) {
        List<String> imagePaths = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String path = fileStorageService.savePostImage(image);
                imagePaths.add(path);
            }
            System.out.println("📷 이미지 " + imagePaths.size() + "개 저장 완료");
        }

        PostCreateRequest postCreateRequest = new PostCreateRequest(
                scope, clubId, title, content, tags, imagePaths
        );

        PostResponse postResponse = postService.createPost(userId, postCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("게시물 생성 성공", postResponse));
    }

    @Operation(summary = "게시물 조회", description = "게시물 id를 통해 정보를 불러옵니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPost(postId);
        return ResponseEntity.ok(new ApiResponse<>("게시물 조회 성공", postResponse));
    }

    @Operation(summary = "전체 게시물 조회", description = "전체 게시물의 정보를 불러옵니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts() {
        List<PostResponse> postResponses = postService.getPosts();
        return ResponseEntity.ok(new ApiResponse<>("게시글 전체 조회 성공", postResponses));
    }

    @Operation(summary = "내 게시물 수정", description = "사용자의 게시물을 수정합니다.")
    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long postId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) {
        List<String> imagePaths = null;
        if (images != null && !images.isEmpty()) {
            imagePaths = new ArrayList<>();
            for (MultipartFile image : images) {
                String path = fileStorageService.savePostImage(image);
                imagePaths.add(path);
            }
            System.out.println("📷 새 이미지 " + imagePaths.size() + "개 저장 완료");
        }

        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(
                title, content, tags, imagePaths
        );

        PostResponse postResponse = postService.updatePost(postId, postUpdateRequest);
        return ResponseEntity.ok(new ApiResponse<>("게시물 수정 성공", postResponse));
    }

    @Operation(summary = "게시물 삭제", description = "게시물 id를 통해 정보를 삭제합니다.")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
