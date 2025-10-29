package com.example.dance_community.controller;

import com.example.dance_community.auth.GetUserId;
import com.example.dance_community.dto.ApiResponse;
import com.example.dance_community.dto.post.PostDto;
import com.example.dance_community.dto.post.PostRequest;
import com.example.dance_community.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ApiResponse<PostDto>> createPost(@GetUserId Long userId, @Valid @RequestBody PostRequest postRequest) {
        PostDto postDto = postService.createPost(userId, postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("게시글 생성 성공", postDto));
    }

    @Operation(summary = "게시글 조회", description = "게시글 id를 통해 정보를 불러옵니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDto>> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPost(postId);
        return ResponseEntity.ok(new ApiResponse<>("게시글 조회 성공", postDto));
    }

    @Operation(summary = "전체 게시글 조회", description = "전체 게시글의 정보를 불러옵니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDto>>> getPosts() {
        List<PostDto> postDtoList = postService.getPosts();
        return ResponseEntity.ok(new ApiResponse<>("게시글 전체 조회 성공", postDtoList));
    }

    @Operation(summary = "내 게시글 수정", description = "사용자의 게시글을 수정합니다.")
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDto>> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequest postRequest) {
        PostDto postDto = postService.updatePost(postId, postRequest);
        return ResponseEntity.ok(new ApiResponse<>("게시글 수정 성공", postDto));
    }

    @Operation(summary = "개시글 삭제", description = "게시글 id를 통해 정보를 삭제합니다.")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
