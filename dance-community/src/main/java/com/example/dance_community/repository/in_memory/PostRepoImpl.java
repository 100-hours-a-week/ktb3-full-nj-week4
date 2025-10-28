package com.example.dance_community.repository.in_memory;

import com.example.dance_community.dto.post.PostDto;
import com.example.dance_community.enums.Scope;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepoImpl implements PostRepo{
    private final Map<Long, PostDto> postMap = new ConcurrentHashMap<>();
    private final AtomicLong postId = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        PostDto defaultPost = PostDto.builder()
                .postId(postId.incrementAndGet())
                .userId(1L)
                .scope(Scope.CLUB)
                .clubId(1L)
                .title("10월 락킹 워크샵 공지")
                .content("00 선생님 워크샵이 사당에서 열립니다!")
                .tags(Arrays.asList("locking", "workshop"))
                .images(Arrays.asList("https://image1.kr/img.jpg", "https://image2.kr/img.jpg"))
                .createdAt(LocalDateTime.now())
                .build();

        this.savePost(defaultPost);
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        if (postDto.getPostId() == null) {
            postDto = postDto.toBuilder()
                    .postId(postId.incrementAndGet())
                    .createdAt(LocalDateTime.now())
                    .build();
        }
        postMap.put(postDto.getPostId(), postDto);
        return postDto;
    }

    @Override
    public Optional<PostDto> findById(Long postId) {
        return Optional.ofNullable(postMap.get(postId));
    }

    @Override
    public List<PostDto> findAll() {
        return new ArrayList<>(postMap.values());
    }

    @Override
    public void deleteById(Long id) {
        postMap.remove(id);
    }
}