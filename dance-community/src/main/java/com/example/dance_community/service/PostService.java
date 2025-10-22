package com.example.dance_community.service;

import com.example.dance_community.dto.post.PostDto;
import com.example.dance_community.dto.post.PostRequest;
import com.example.dance_community.enums.Scope;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepo postRepo;

    @Autowired
    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public PostDto createPost(Long userId, PostRequest postRequest) {
        try {
            PostDto newPost = PostDto.builder()
                    .userId(userId)
                    .scope(Scope.valueOf(postRequest.getScope()))
                    .clubId(postRequest.getClubId())
                    .title(postRequest.getTitle())
                    .content(postRequest.getContent())
                    .tags(postRequest.getTags())
                    .images(postRequest.getImages())
                    .build();

            return postRepo.savePost(newPost);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        } catch (Exception e) {
            throw new RuntimeException("행사 생성 실패");
        }
    }

    public PostDto getPost(Long postId) {
        return postRepo.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 조회 실패"));
    }

    public List<PostDto> getPosts() {
        try {
            return postRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("게시글 전체 조회 실패");
        }
    }

    public PostDto updatePost(Long postId, PostRequest postRequest) {
        PostDto postDto = postRepo.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 조회 실패"));

        try {
            Scope newScope = postRequest.getScope() != null ? Scope.valueOf(postRequest.getScope()) : postDto.getScope();

            PostDto updatedPost = postDto.toBuilder()
                    .scope(newScope)
                    .title(postRequest.getTitle() != null ? postRequest.getTitle() : postDto.getTitle())
                    .content(postRequest.getContent() != null ? postRequest.getContent() : postDto.getContent())
                    .tags(postRequest.getTags() != null ? postRequest.getTags() : postDto.getTags())
                    .images(postRequest.getImages() != null ? postRequest.getImages() : postDto.getImages())
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
