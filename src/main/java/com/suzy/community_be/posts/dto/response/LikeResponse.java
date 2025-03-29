package com.suzy.community_be.posts.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponse {
    private boolean isLike;
    private int likes;

    @Builder
    public LikeResponse(boolean isLike, int likes) {
        this.isLike = isLike;
        this.likes = likes;
    }
} 