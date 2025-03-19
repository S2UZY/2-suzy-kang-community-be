package com.hello.__suzy_kang_community_be.gobal.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private  String message;
    private boolean success;
    private  Object data;
}
