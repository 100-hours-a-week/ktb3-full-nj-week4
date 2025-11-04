package com.example.dance_community.dto.post;

import com.example.dance_community.entity.Post;
import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(
        Long postId,
        Long authorId,
        String authorName,
        String scope,
        Long clubId,
        String clubName,
        String title,
        String content,
        List<String> tags,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getAuthor().getUserId(),
                post.getAuthor().getUsername(),
                post.getScope().name(),
                post.getClub() != null ? post.getClub().getClubId() : null,
                post.getClub() != null ? post.getClub().getClubName() : null,
                post.getTitle(),
                post.getContent(),
                post.getTags(),
                post.getImages(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}