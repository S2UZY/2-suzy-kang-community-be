package com.suzy.community_be.users.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String profile;
}
