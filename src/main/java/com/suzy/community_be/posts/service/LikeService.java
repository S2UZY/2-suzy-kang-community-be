package com.suzy.community_be.posts.service;

import com.suzy.community_be.gobal.exception.CustomException;
import com.suzy.community_be.gobal.exception.ErrorCode;
import com.suzy.community_be.posts.dto.response.LikeResponse;
import com.suzy.community_be.posts.entity.Like;
import com.suzy.community_be.posts.entity.Post;
import com.suzy.community_be.posts.repository.LikeRepository;
import com.suzy.community_be.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public LikeResponse toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        
        boolean isLiked = likeRepository.existsByPostIdAndUserId(postId, userId);
        
        boolean newLikeStatus;

        if (isLiked) {
            Optional<Like> likeOpt = likeRepository.findByPostIdAndUserId(postId, userId);
            if (likeOpt.isPresent()) {
                Like like = likeOpt.get();
                likeRepository.delete(like);
                likeRepository.flush(); 
            }
            newLikeStatus = false;
        } else {
            Like like = new Like(post, userId);
            likeRepository.save(like);
            likeRepository.flush(); 
            newLikeStatus = true;
        }

        postRepository.flush(); 
        postRepository.findById(postId); 
        
        int likeCount = likeRepository.countByPostId(postId);
        
        return LikeResponse.builder()
                .isLike(newLikeStatus)
                .likes(likeCount)
                .build();
    }

    @Transactional(readOnly = true)
    public LikeResponse getLikeStatus(Long postId, Long userId) {
        boolean isLiked = likeRepository.existsByPostIdAndUserId(postId, userId);
        
        int likeCount = likeRepository.countByPostId(postId);

        return LikeResponse.builder()
                .isLike(isLiked)
                .likes(likeCount)
                .build();
    }
} 