package com.example.dance_community.dto.post;

import com.example.dance_community.validation.ValidScopePost;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@ValidScopePost
public class PostRequest {
    @NotBlank(message = "게시글 범위 미입력")
    private String scope;

    private Long clubId;

    @NotBlank(message = "게시글 제목 미입력")
    private String title;

    @NotBlank(message = "게시글 내용 미입력")
    private String content;

    private List<String> tags;
    private List<String> images;
}