package com.suzy.community_be.gobal.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private  String message;
    private boolean success;
    private  Object data;
}
