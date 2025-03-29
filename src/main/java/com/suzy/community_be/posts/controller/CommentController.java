package com.suzy.community_be.posts.controller;

import com.suzy.community_be.gobal.response.ApiResponse;
import com.suzy.community_be.posts.dto.request.CommentCreateRequest;
import com.suzy.community_be.posts.dto.request.CommentUpdateRequest;
import com.suzy.community_be.posts.dto.response.CommentResponse;
import com.suzy.community_be.posts.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(new ApiResponse("comments_get_success", comments));
    }
    
    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest request
    ) {
        Long commentId = commentService.createComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("comment_post_success", Map.of("comment_id", commentId)));
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request
    ) {
        commentService.updateComment(postId, commentId, request);
        return ResponseEntity.ok(new ApiResponse("comment_patch_success", null));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(new ApiResponse("comment_delete_success", null));
    }
} 