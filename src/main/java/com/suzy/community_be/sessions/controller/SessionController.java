package com.suzy.community_be.sessions.controller;

import com.suzy.community_be.gobal.response.ApiResponse;
import com.suzy.community_be.sessions.dto.request.LoginRequestDto;
import com.suzy.community_be.sessions.dto.response.LoginResponseDto;
import com.suzy.community_be.sessions.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        LoginResponseDto responseDto = sessionService.login(requestDto, response);
        return ResponseEntity.ok(new ApiResponse("login_success",true,responseDto));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> logout(HttpServletResponse response){
        sessionService.logout(response);
        return ResponseEntity.ok(new ApiResponse("logout_success", true,null));
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse> checkLogin(HttpServletRequest request){
        boolean isLoggedIn = sessionService.checkLogin(request);
        Map<String, Boolean> result = new HashMap<>();
        result.put("isLoggedIn", isLoggedIn);

        return ResponseEntity.ok(new ApiResponse("login_check_success", true, result));
    }

}
