package com.example.dance_community.dto.post;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostUpdateRequest {
    @NotBlank(message = "게시물 제목 미입력")
    private String title;

    @NotBlank(message = "게시물 내용 미입력")
    private String content;

    private List<String> tags;
    private List<String> newImagePaths;
    private List<String> keepImages;
}