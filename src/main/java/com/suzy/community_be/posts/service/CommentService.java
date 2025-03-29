package com.suzy.community_be.posts.service;

import com.suzy.community_be.gobal.exception.CustomException;
import com.suzy.community_be.gobal.exception.ErrorCode;
import com.suzy.community_be.posts.dto.request.CommentCreateRequest;
import com.suzy.community_be.posts.dto.request.CommentUpdateRequest;
import com.suzy.community_be.posts.dto.response.CommentResponse;
import com.suzy.community_be.posts.entity.Comment;
import com.suzy.community_be.posts.entity.Post;
import com.suzy.community_be.posts.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        
        return post.getComments().stream()
                .map(comment -> {
                    User author = userRepository.findById(comment.getUserId())
                            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
                    return CommentResponse.from(comment, author);
                })
                .collect(Collectors.toList());
    }
    
    @Transactional
    public Long createComment(Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .post(post)
                .userId(request.getUserId())
                .content(request.getContent())
                .build();

        return commentRepository.save(comment).getId();
    }
    
    @Transactional
    public void updateComment(Long postId, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        
        comment.update(request.getContent());
    }
    
    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        
        commentRepository.delete(comment);
    }
} 