package com.suzy.community_be.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suzy.community_be.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final int STATUS_UNAUTHORIZED = 401;
    private static final int STATUS_FORBIDDEN = 403;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(List.of("http://localhost:3000"));
            config.setAllowCredentials(true);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // 세션 관리 설정 - 상태를 저장하지 않음
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // HTTP 요청 권한 설정
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/users", "/sessions").permitAll()
                                .anyRequest().authenticated())

                // CORS 설정 적용
                .cors(corsConfigurer ->
                        corsConfigurer.configurationSource(corsConfigurationSource()))

                // 기본 HTTP 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                // 예외 처리 설정
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                // 인증 실패 처리 (401 Unauthorized)
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.setStatus(STATUS_UNAUTHORIZED);
                                    response.setContentType("application/json;charset=UTF-8");

                                    Map<String, Object> errorResponse = new HashMap<>();
                                    errorResponse.put("message", "unauthorized");
                                    errorResponse.put("error", "인증에 실패했습니다. 유효한 토큰이 필요합니다.");
                                    errorResponse.put("data", null);

                                    String jsonResponse = objectMapper.writeValueAsString(errorResponse);
                                    response.getWriter().write(jsonResponse);
                                })
                                // 권한 부족 처리 (403 Forbidden)
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.setStatus(STATUS_FORBIDDEN);
                                    response.setContentType("application/json;charset=UTF-8");

                                    Map<String, Object> errorResponse = new HashMap<>();
                                    errorResponse.put("message", "access_denied");
                                    errorResponse.put("error", "해당 리소스에 접근할 권한이 없습니다.");
                                    errorResponse.put("data", null);

                                    String jsonResponse = objectMapper.writeValueAsString(errorResponse);
                                    response.getWriter().write(jsonResponse);
                                }))

                // JWT 인증 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}