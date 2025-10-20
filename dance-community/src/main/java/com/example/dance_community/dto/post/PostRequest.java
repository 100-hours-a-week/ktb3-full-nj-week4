package com.example.dance_community.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostRequest {
    private String scope;
    private Long clubId;
    private String title;
    private String content;
    private List<String> tags;
    private List<String> images;
}
