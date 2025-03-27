package com.suzy.community_be.security;

import com.suzy.community_be.gobal.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        } else {
            token = jwtUtil.extractTokenFromCookie(request);
        }

        if(token != null) {
            try {
                Long userId = jwtUtil.extractUserId(token);
                request.setAttribute("userId", userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                System.out.println("JWT 인증 실패: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}