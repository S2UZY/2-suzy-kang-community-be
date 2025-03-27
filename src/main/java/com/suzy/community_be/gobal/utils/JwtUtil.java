package com.suzy.community_be.gobal.utils;

import com.suzy.community_be.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY;
    private final long EXPIRATION_TIME;
    private static final String COOKIE_NAME = "auth_token";

    @Autowired
    public JwtUtil(JwtConfig jwtConfig){
        this.SECRET_KEY = jwtConfig.getSecretKey();
        this.EXPIRATION_TIME = jwtConfig.getExpirationTime();
    }

    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public Long extractUserId(String token){
       Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody();

       return Long.parseLong(claims.getSubject());
    }

    /// 토큰 만료 여부 확인
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void addTokenToCookie(HttpServletResponse response, String token){
        response.setHeader("Set-Cookie", String.format("%s=%s; Max-Age=%d; Path=/; HttpOnly; SameSite=Lax",
        COOKIE_NAME, token, (int)(EXPIRATION_TIME / 1000)));
    }

    public String extractTokenFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for (Cookie cookie: cookies){
                if(COOKIE_NAME.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void deleteCookie(HttpServletResponse response){
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        System.out.println("쿠키 삭제 완료");
    }

}
