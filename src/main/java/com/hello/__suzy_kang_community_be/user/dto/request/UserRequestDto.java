package com.hello.__suzy_kang_community_be.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String profile;
}
