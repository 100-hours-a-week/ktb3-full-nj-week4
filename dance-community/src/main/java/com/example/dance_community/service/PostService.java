package com.example.dance_community.service;

import com.example.dance_community.dto.post.PostCreateRequest;
import com.example.dance_community.dto.post.PostResponse;
import com.example.dance_community.dto.post.PostUpdateRequest;
import com.example.dance_community.entity.Club;
import com.example.dance_community.entity.Post;
import com.example.dance_community.entity.User;
import com.example.dance_community.entity.enums.Scope;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.jpa.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final ClubService ClubService;

    @Transactional
    public PostResponse createPost(Long userId, PostCreateRequest request) {
        User author = userService.getActiveUser(userId);

        Club club = null;
        if (Scope.CLUB.toString().equals(request.getScope())) {
            Long clubId = request.getClubId();
            if (clubId == null) {
                throw new InvalidRequestException("공개 범위가 CLUB일 경우 clubId가 필요");
            }
            club = ClubService.getActiveClub(clubId);
        }

        Post post = Post.builder()
                .author(author)
                .scope(Scope.valueOf(request.getScope().toUpperCase()))
                .club(club)
                .title(request.getTitle())
                .content(request.getContent())
                .tags(request.getTags())
                .images(request.getImages())
                .build();

        Post newPost = postRepository.save(post);
        return PostResponse.from(newPost);
    }

    public PostResponse getPost(Long postId) {
        Post post = getActivePost(postId);
        return PostResponse.from(post);
    }

    public List<PostResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostResponse::from).toList();
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostUpdateRequest request) {
        Post post = getActivePost(postId);

        post.updatePost(
                request.getTitle(),
                request.getContent(),
                request.getTags(),
                request.getImages()
        );

        return PostResponse.from(post);
    }

    public void deletePost(Long postId) {
        Post post = getActivePost(postId);
        post.delete();
    }

    private Post getActivePost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다"));
    }
}
