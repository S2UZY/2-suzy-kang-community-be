package com.suzy.community_be.sessions.controller;

import com.suzy.community_be.gobal.response.ApiResponse;
import com.suzy.community_be.sessions.dto.request.LoginRequestDto;
import com.suzy.community_be.sessions.dto.response.LoginResponseDto;
import com.suzy.community_be.sessions.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestDto requestDto){
        LoginResponseDto responseDto = sessionService.login(requestDto);
        return ResponseEntity.ok(new ApiResponse("login_success",true,responseDto));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> logout(){
        sessionService.logout();
        return ResponseEntity.ok(new ApiResponse("logout_success", true,null));
    }
}
