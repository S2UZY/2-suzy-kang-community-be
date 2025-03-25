package com.suzy.community_be.posts.dto.response;

import com.suzy.community_be.posts.entity.Like;
import com.suzy.community_be.posts.entity.Post;
import com.suzy.community_be.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostDetailResponse {
    private Long id;
    private Long authorId;
    private String profile;
    private String nickname;
    private String title;
    private String content;
    private String image;
    private String date;
    private int views;
    private int likes;
    private List<Long> likedBy;
    private List<CommentResponse> comments;

    @Builder
    public PostDetailResponse(Long id, Long authorId, String profile, String nickname,
                            String title, String content, String image, String date,
                            int views, int likes, List<Long> likedBy, List<CommentResponse> comments) {
        this.id = id;
        this.authorId = authorId;
        this.profile = profile;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.image = image;
        this.date = date;
        this.views = views;
        this.likes = likes;
        this.likedBy = likedBy;
        this.comments = comments;
    }

    public static PostDetailResponse from(Post post, User author, List<CommentResponse> comments) {
        List<Long> likedBy = post.getLikes().stream()
                .map(Like::getUserId)
                .collect(Collectors.toList());

        return PostDetailResponse.builder()
                .id(post.getId())
                .authorId(post.getUserId())
                .profile(author.getProfile())
                .nickname(author.getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .date(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .views(post.getViews().intValue())
                .likes(post.getLikes().size())
                .likedBy(likedBy)
                .comments(comments)
                .build();
    }
} 