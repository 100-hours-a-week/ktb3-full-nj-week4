package com.example.dance_community.repository.in_memory;

import com.example.dance_community.dto.post.PostDto;

import java.util.List;
import java.util.Optional;

public interface PostRepo {
    PostDto savePost(PostDto postDto);
    Optional<PostDto> findById(Long postId);
    List<PostDto> findAll();
    void deleteById(Long id);
}