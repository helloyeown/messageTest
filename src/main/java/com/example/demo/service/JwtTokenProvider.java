package com.example.demo.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {
    
    // 토큰을 암호화하는 데 사용되는 비밀 키
    private final byte[] secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

    public String createToken(String name) {
        String token = Jwts.builder()
            .setSubject(name)
            .setIssuer("app-name")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3600000))  // 1 hour
            .signWith(Keys.hmacShaKeyFor(secretKey))    // 비밀키로 토큰을 서명
            .compact();

        return token;
    }
}
