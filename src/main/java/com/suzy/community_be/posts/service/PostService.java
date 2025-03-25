package com.suzy.community_be.posts.service;

import com.suzy.community_be.posts.dto.request.CommentCreateRequest;
import com.suzy.community_be.posts.dto.request.CommentUpdateRequest;
import com.suzy.community_be.posts.dto.request.PostCreateRequest;
import com.suzy.community_be.posts.dto.request.PostUpdateRequest;
import com.suzy.community_be.posts.dto.response.CommentResponse;
import com.suzy.community_be.posts.dto.response.PostDetailResponse;
import com.suzy.community_be.posts.dto.response.PostListResponse;
import com.suzy.community_be.posts.dto.response.PostUpdateResponse;
import com.suzy.community_be.posts.entity.Comment;
import com.suzy.community_be.posts.entity.Like;
import com.suzy.community_be.posts.entity.Post;
import com.suzy.community_be.posts.repository.CommentRepository;
import com.suzy.community_be.posts.repository.LikeRepository;
import com.suzy.community_be.posts.repository.PostRepository;
import com.suzy.community_be.users.entity.User;
import com.suzy.community_be.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional()
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

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
        return postRepository.findAll().stream()
                .map(post -> {
                    User author = userRepository.findById(post.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
                    return PostListResponse.from(post, author);
                })
                .collect(Collectors.toList());
    }

    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new IllegalStateException("Already liked");
        }
        
        likeRepository.save(new Like(post, userId));
    }

    public void unlikePost(Long postId, Long userId) {
        Like like = likeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));
        likeRepository.delete(like);
    }

    public Long createComment(Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = Comment.builder()
                .post(post)
                .userId(request.getUserId())
                .content(request.getContent())
                .build();

        return commentRepository.save(comment).getId();
    }

    public void updateComment(Long postId, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        
        comment.update(request.getContent());
    }

    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        commentRepository.delete(comment);
    }
} 