package com.suzy.community_be.posts.dto.response;

import com.suzy.community_be.posts.entity.Post;
import com.suzy.community_be.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostListResponse {
    private Long id;
    private String profile;
    private String nickname;
    private String title;
    private int views;
    private int likes;
    private int commentLength;
    private String date;

    @Builder
    public PostListResponse(Long id, String profile, String nickname, 
                          String title, int views, int likes, int commentLength, String date) {
        this.id = id;
        this.profile = profile;
        this.nickname = nickname;
        this.title = title;
        this.views = views;
        this.likes = likes;
        this.commentLength = commentLength;
        this.date = date;
    }

    public static PostListResponse from(Post post, User author) {
        try {
            return PostListResponse.builder()
                    .id(post.getId())
                    .profile(author.getProfile())
                    .nickname(author.getNickname())
                    .title(post.getTitle())
                    .views(post.getViews().intValue())
                    .likes(post.getLikes() != null ? post.getLikes().size() : 0)
                    .commentLength(post.getComments() != null ? post.getComments().size() : 0)
                    .date(post.getCreatedAt() != null ? post.getCreatedAt().toString() : "")
                    .build();
        } catch (Exception e) {
            System.err.println("Error creating PostListResponse: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
} 