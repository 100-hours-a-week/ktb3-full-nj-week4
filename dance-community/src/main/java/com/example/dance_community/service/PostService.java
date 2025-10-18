package com.example.dance_community.service;

import com.example.dance_community.dto.post.PostDto;
import com.example.dance_community.dto.post.PostRequest;
import com.example.dance_community.enums.Scope;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto createPost(Long userId, PostRequest postRequest) {
        if (postRequest.getScope() == null || postRequest.getTitle() == null || postRequest.getContent() == null) {
            throw new InvalidRequestException("필수 필드 누락");
        }

        PostDto postDto = new PostDto();
        postDto.setUserId(userId);
        postDto.setScope(Scope.valueOf(postRequest.getScope()));
        postDto.setClubId(postRequest.getClubId());
        postDto.setTitle(postRequest.getTitle());
        postDto.setContent(postRequest.getContent());
        postDto.setTags(postRequest.getTags());
        postDto.setImages(postRequest.getImages());
        PostDto newPost = postRepository.savePost(postDto);

        return newPost;
    }

    public PostDto getPost(Long postId) {
        PostDto postDto = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 조회 실패"));
        return postDto;
    }

    public List<PostDto> getPosts() {
        try {
            return postRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("게시글 전체 조회 실패");
        }
    }

    public PostDto updatePost(Long postId, PostRequest postRequest) {
        PostDto postDto = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 조회 실패"));

        try {
            if (postRequest.getScope() != null) postDto.setScope(Scope.valueOf(postRequest.getScope()));
            if (postRequest.getTitle() != null) postDto.setTitle(postRequest.getTitle());
            if (postRequest.getContent() != null) postDto.setContent(postRequest.getContent());
            if (postRequest.getTags() != null) postDto.setTags(postRequest.getTags());
            if (postRequest.getImages() != null) postDto.setImages(postRequest.getImages());
        } catch (Exception e) {
            throw new InvalidRequestException("잘못된 요청 데이터");
        }

        postDto.setUpdatedAt(LocalDateTime.now());
        postRepository.savePost(postDto);
        return postDto;
    }

    public void deletePost(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 삭제 실패"));
        postRepository.deleteById(postId);
    }
}
