package com.example.dance_community.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostsResponse {
    private String message;
    private List<PostDto> data;
}
