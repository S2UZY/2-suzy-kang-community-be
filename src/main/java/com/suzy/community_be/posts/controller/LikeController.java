package com.suzy.community_be.posts.controller;

import com.suzy.community_be.gobal.response.ApiResponse;
import com.suzy.community_be.posts.dto.response.LikeResponse;
import com.suzy.community_be.posts.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse> toggleLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        LikeResponse response = likeService.toggleLike(postId, userId);
        return ResponseEntity.ok(new ApiResponse("like_toggle_success", response));
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse> getLikeStatus(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        LikeResponse response = likeService.getLikeStatus(postId, userId);
        return ResponseEntity.ok(new ApiResponse("like_status_success", response));
    }
} 