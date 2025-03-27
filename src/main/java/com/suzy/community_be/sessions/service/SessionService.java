package com.suzy.community_be.sessions.service;

import com.suzy.community_be.gobal.exception.CustomException;
import com.suzy.community_be.gobal.exception.ErrorCode;
import com.suzy.community_be.gobal.utils.JwtUtil;
import com.suzy.community_be.sessions.dto.request.LoginRequestDto;
import com.suzy.community_be.sessions.dto.response.LoginResponseDto;
import com.suzy.community_be.users.entity.User;
import com.suzy.community_be.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response){
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!user.getPassword().equals(requestDto.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtUtil.generateToken(user.getId());
        jwtUtil.addTokenToCookie(response, token);

        return new LoginResponseDto(user.getId(), user.getEmail(), user.getNickname(), user.getProfile());
    }

    public void logout(HttpServletResponse response){
        jwtUtil.deleteCookie(response);
        SecurityContextHolder.clearContext();
    }

    public boolean checkLogin(HttpServletRequest request){
        String token = jwtUtil.extractTokenFromCookie(request);
        if(token == null){
            return false;
        }

        try{
            Long userId = jwtUtil.extractUserId(token);
            return !jwtUtil.isTokenExpired(token) && userRepository.existsById(userId);
        } catch (Exception e) {
            return false;
        }
    }
}
