package com.example.dance_community.dto.post;

import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public class PostsResponse {
    private String message;
    private List<PostDto> data;
}
