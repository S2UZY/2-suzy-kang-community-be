package com.suzy.community_be.posts.controller;

import com.suzy.community_be.gobal.response.ApiResponse;
import com.suzy.community_be.posts.dto.request.CommentCreateRequest;
import com.suzy.community_be.posts.dto.request.CommentUpdateRequest;
import com.suzy.community_be.posts.dto.request.PostCreateRequest;
import com.suzy.community_be.posts.dto.request.PostUpdateRequest;
import com.suzy.community_be.posts.dto.response.PostDetailResponse;
import com.suzy.community_be.posts.dto.response.PostListResponse;
import com.suzy.community_be.posts.dto.response.PostUpdateResponse;
import com.suzy.community_be.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse> createPost(@RequestBody PostCreateRequest request) {
        Long postId = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("post_post_success", true, Map.of("post_id", postId)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse> getPost(@PathVariable Long postId) {
        PostDetailResponse response = postService.getPost(postId);
        return ResponseEntity.ok(new ApiResponse("post_get_success", true,response));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        PostUpdateResponse response = postService.updatePost(postId, request);
        return ResponseEntity.ok(new ApiResponse("post_patch_success", true,response));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("post_delete_success", true, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getPosts() {
        List<PostListResponse> response = postService.getPosts();
        return ResponseEntity.ok(new ApiResponse("posts_get_success", true, response));
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse> likePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.likePost(postId, userId);
        return ResponseEntity.ok(new ApiResponse("post_like_success", true, null));
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse> unlikePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.unlikePost(postId, userId);
        return ResponseEntity.ok(new ApiResponse("post_unlike_success", true, null));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest request
    ) {
        Long commentId = postService.createComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("comment_post_success", true, Map.of("comment_id", commentId)));
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request
    ) {
        postService.updateComment(postId, commentId, request);
        return ResponseEntity.ok(new ApiResponse("comment_patch_success", true, null));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        postService.deleteComment(postId, commentId);
        return ResponseEntity.ok(new ApiResponse("comment_delete_success", true,null));
    }
}
