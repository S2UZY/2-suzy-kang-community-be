package com.suzy.community_be.posts.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {
    private Long authorId;
    private String title;
    private String content;
    private String image;
} 