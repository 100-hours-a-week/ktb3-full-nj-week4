package com.example.dance_community.controller;

import com.example.dance_community.dto.post.PostDto;
import com.example.dance_community.dto.post.PostRequest;
import com.example.dance_community.dto.post.PostResponse;
import com.example.dance_community.dto.post.PostsResponse;
import com.example.dance_community.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/{userId}")
    public ResponseEntity<PostResponse> createPost(@PathVariable Long userId, @Valid @RequestBody PostRequest postRequest) {
        PostDto postDto = postService.createPost(userId, postRequest);
        return ResponseEntity.status(201).body(new PostResponse("게시글 생성 성공", postDto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPost(postId);
        return ResponseEntity.ok(new PostResponse("게시글 조회 성공", postDto));
    }

    @GetMapping
    public ResponseEntity<PostsResponse> getPosts() {
        List<PostDto> postDtos = postService.getPosts();
        return ResponseEntity.ok(new PostsResponse("게시글 전체 조회 성공", postDtos));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequest postRequest) {
        PostDto postDto = postService.updatePost(postId, postRequest);
        return ResponseEntity.ok(new PostResponse("게시글 수정 성공", postDto));
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
