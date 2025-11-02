package com.example.dance_community.repository.in_memory;

import com.example.dance_community.dto.post.PostCreateRequest;
import com.example.dance_community.entity.enums.Scope;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepoImpl implements PostRepo{
    private final Map<Long, PostCreateRequest> postMap = new ConcurrentHashMap<>();
    private final AtomicLong postId = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        PostCreateRequest defaultPost = PostCreateRequest.builder()
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
    public PostCreateRequest savePost(PostCreateRequest postCreateRequest) {
        if (postCreateRequest.getPostId() == null) {
            postCreateRequest = postCreateRequest.toBuilder()
                    .postId(postId.incrementAndGet())
                    .createdAt(LocalDateTime.now())
                    .build();
        }
        postMap.put(postCreateRequest.getPostId(), postCreateRequest);
        return postCreateRequest;
    }

    @Override
    public Optional<PostCreateRequest> findById(Long postId) {
        return Optional.ofNullable(postMap.get(postId));
    }

    @Override
    public List<PostCreateRequest> findAll() {
        return new ArrayList<>(postMap.values());
    }

    @Override
    public void deleteById(Long id) {
        postMap.remove(id);
    }
}