package com.example.dance_community.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostRequest {
    private String scope;
    private Long clubId;
    private String title;
    private String content;
    private List<String> tags;
    private List<String> images;
}
