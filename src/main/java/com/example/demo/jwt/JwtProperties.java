package com.example.demo.jwt;

public interface JwtProperties {
    String SECRET = "yewontest";
    int EXPIRATION_TIME = 864000000;    // 10 days
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
