package com.example.dance_community.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponse {
    private String message;
    private PostDto data;
}
