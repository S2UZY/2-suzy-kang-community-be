package com.suzy.community_be.posts.dto.response;

import com.suzy.community_be.posts.entity.Comment;
import com.suzy.community_be.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private Long authorId;
    private String profile;
    private String nickname;
    private String content;
    private String date;

    @Builder
    public CommentResponse(Long id, Long authorId, String profile, String nickname, String content, String date) {
        this.id = id;
        this.authorId = authorId;
        this.profile = profile;
        this.nickname = nickname;
        this.content = content;
        this.date = date;
    }

    public static CommentResponse from(Comment comment, User author) {
        return CommentResponse.builder()
                .id(comment.getId())
                .authorId(comment.getUserId())
                .profile(author.getProfile())
                .nickname(author.getNickname())
                .content(comment.getContent())
                .date(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
} 