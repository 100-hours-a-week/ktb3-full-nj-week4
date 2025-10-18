package com.example.dance_community.dto.comment;

import java.time.LocalDateTime;

public class CommentDto {
    private String comment;

    private int commentId;
    private int postId;
    private int userId;

    private LocalDateTime createdAt;
}
