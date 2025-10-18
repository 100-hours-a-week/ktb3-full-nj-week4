package com.example.dance_community.repository;

import com.example.dance_community.dto.post.PostDto;
import com.example.dance_community.enums.Scope;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository{
    private final Map<Long, PostDto> postMap = new ConcurrentHashMap<>();
    private final AtomicLong postId = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        PostDto defaultPost = new PostDto();
        defaultPost.setPostId(postId.incrementAndGet());
        defaultPost.setUserId(1L);
        defaultPost.setScope(Scope.CLUB);
        defaultPost.setClubId(1L);
        defaultPost.setTitle("10월 락킹 워크샵 공지");
        defaultPost.setContent("00 선생님 워크샵이 사당에서 열립니다!");
        defaultPost.setTags(Arrays.asList("locking", "workshop"));
        defaultPost.setImages(Arrays.asList("https://image1.kr/img.jpg", "https://image2.kr/img.jpg"));
        defaultPost.setCreatedAt(LocalDateTime.now());

        postMap.put(defaultPost.getPostId(), defaultPost);
    }

    public PostDto savePost(PostDto postDto) {
        if (postDto.getPostId() == null) {
            postDto.setPostId(postId.incrementAndGet());
            postDto.setCreatedAt(LocalDateTime.now());
        }
        postMap.put(postDto.getPostId(), postDto);
        return postDto;
    }

    public Optional<PostDto> findById(Long postId) {
        return Optional.ofNullable(postMap.get(postId));
    }

    public List<PostDto> findAll() {
        return new ArrayList<>(postMap.values());
    }

    public void deleteById(Long id) {
        postMap.remove(id);
    }
}