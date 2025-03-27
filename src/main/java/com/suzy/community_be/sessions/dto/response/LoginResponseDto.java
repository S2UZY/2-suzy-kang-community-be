package com.suzy.community_be.sessions.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private String profile;
}
