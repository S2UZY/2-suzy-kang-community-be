package com.suzy.community_be.posts.dto.response;

import com.suzy.community_be.posts.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateResponse {
    private String title;
    private String content;
    private String image;

    @Builder
    public PostUpdateResponse(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public static PostUpdateResponse from(Post post) {
        return PostUpdateResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .build();
    }
} 