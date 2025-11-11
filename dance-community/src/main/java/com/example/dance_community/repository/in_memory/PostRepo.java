package com.example.dance_community.repository.in_memory;

import com.example.dance_community.dto.post.PostCreateRequest;

import java.util.List;
import java.util.Optional;

public interface PostRepo {
    PostCreateRequest savePost(PostCreateRequest postCreateRequest);
    Optional<PostCreateRequest> findById(Long postId);
    List<PostCreateRequest> findAll();
    void deleteById(Long id);
}