package com.suzy.community_be.users.dto.response;

import com.suzy.community_be.users.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String profile;

    public static UserResponseDto from(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profile(user.getProfile())
                .build();
    }
}
