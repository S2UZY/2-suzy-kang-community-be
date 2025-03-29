package com.suzy.community_be.posts.controller;

import com.suzy.community_be.gobal.response.ApiResponse;
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
                .body(new ApiResponse("post_post_success", Map.of("post_id", postId)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse> getPost(@PathVariable Long postId) {
        PostDetailResponse response = postService.getPost(postId);
        return ResponseEntity.ok(new ApiResponse("post_get_success", response));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        PostUpdateResponse response = postService.updatePost(postId, request);
        return ResponseEntity.ok(new ApiResponse("post_patch_success", response));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("post_delete_success", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getPosts() {
        List<PostListResponse> response = postService.getPosts();
        return ResponseEntity.ok(new ApiResponse("posts_get_success", response));
    }
}
