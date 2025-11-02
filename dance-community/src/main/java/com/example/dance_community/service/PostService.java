package com.example.dance_community.service;

import com.example.dance_community.dto.post.PostCreateRequest;
import com.example.dance_community.dto.post.PostUpdateRequest;
import com.example.dance_community.entity.enums.Scope;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.jpa.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostCreateRequest createPost(Long userId, PostUpdateRequest postUpdateRequest) {
        try {
            PostCreateRequest newPost = PostCreateRequest.builder()
                    .userId(userId)
                    .scope(Scope.valueOf(postUpdateRequest.getScope()))
                    .clubId(postUpdateRequest.getClubId())
                    .title(postUpdateRequest.getTitle())
                    .content(postUpdateRequest.getContent())
                    .tags(postUpdateRequest.getTags())
                    .images(postUpdateRequest.getImages())
                    .build();

            return postRepository.savePost(newPost);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        } catch (Exception e) {
            throw new RuntimeException("행사 생성 실패");
        }
    }

    public PostCreateRequest getPost(Long postId) {
        return postRepo.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 조회 실패"));
    }

    public List<PostCreateRequest> getPosts() {
        try {
            return postRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("게시글 전체 조회 실패");
        }
    }

    public PostCreateRequest updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        PostCreateRequest postCreateRequest = postRepo.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 조회 실패"));

        try {
            Scope newScope = postUpdateRequest.getScope() != null ? Scope.valueOf(postUpdateRequest.getScope()) : postCreateRequest.getScope();

            PostCreateRequest updatedPost = postCreateRequest.toBuilder()
                    .scope(newScope)
                    .title(postUpdateRequest.getTitle() != null ? postUpdateRequest.getTitle() : postCreateRequest.getTitle())
                    .content(postUpdateRequest.getContent() != null ? postUpdateRequest.getContent() : postCreateRequest.getContent())
                    .tags(postUpdateRequest.getTags() != null ? postUpdateRequest.getTags() : postCreateRequest.getTags())
                    .images(postUpdateRequest.getImages() != null ? postUpdateRequest.getImages() : postCreateRequest.getImages())
                    .updatedAt(LocalDateTime.now())
                    .build();

            postRepo.savePost(updatedPost);
            return updatedPost;
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        } catch (Exception e) {
            throw new RuntimeException("게시글 수정 실패");
        }
    }


    public void deletePost(Long postId) {
        postRepo.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 삭제 실패"));
        postRepo.deleteById(postId);
    }
}
