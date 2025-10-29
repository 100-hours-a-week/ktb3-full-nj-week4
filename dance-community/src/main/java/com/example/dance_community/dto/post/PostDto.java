package com.example.dance_community.dto.post;

import com.example.dance_community.entity.enums.Scope;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class PostDto {
    private Scope scope;

    private String title;
    private String content;

    private List<String> tags;
    private List<String> images;

    private Long postId;
    private Long userId;
    private Long clubId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}