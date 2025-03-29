package com.suzy.community_be.posts.service;

import com.suzy.community_be.posts.dto.request.PostCreateRequest;
import com.suzy.community_be.posts.dto.request.PostUpdateRequest;
import com.suzy.community_be.posts.dto.response.CommentResponse;
import com.suzy.community_be.posts.dto.response.PostDetailResponse;
import com.suzy.community_be.posts.dto.response.PostListResponse;
import com.suzy.community_be.posts.dto.response.PostUpdateResponse;
import com.suzy.community_be.posts.entity.Post;
import com.suzy.community_be.posts.repository.PostRepository;
import com.suzy.community_be.users.entity.User;
import com.suzy.community_be.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createPost(PostCreateRequest request) {
        Post post = Post.builder()
                .userId(request.getAuthorId())
                .title(request.getTitle())
                .content(request.getContent())
                .image(request.getImage())
                .views(0L)
                .build();

        return postRepository.save(post).getId();
    }

    public PostDetailResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        User author = userRepository.findById(post.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        List<CommentResponse> comments = post.getComments().stream()
                .map(comment -> {
                    User commentAuthor = userRepository.findById(comment.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
                    return CommentResponse.from(comment, commentAuthor);
                })
                .collect(Collectors.toList());
        
        post.incrementViews();
        
        return PostDetailResponse.from(post, author, comments);
    }

    public PostUpdateResponse updatePost(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        post.update(request.getTitle(), request.getContent(), request.getImage());
        
        return PostUpdateResponse.from(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<PostListResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        
        Set<Long> userIds = posts.stream()
                .map(Post::getUserId)
                .collect(Collectors.toSet());
        
        Map<Long, User> userMap = userRepository.findAllById(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        
        return posts.stream()
                .filter(post -> userMap.containsKey(post.getUserId()))
                .map(post -> {
                    User author = userMap.get(post.getUserId());
                    
                    return PostListResponse.from(post, author);
                })
                .collect(Collectors.toList());
    }
} 