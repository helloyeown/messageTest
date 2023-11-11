package com.example.demo.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.model.entity.EntitySample;
import com.example.demo.repository.RepositorySample;

import io.jsonwebtoken.io.IOException;
import lombok.extern.log4j.Log4j2;

// 인가 기능
@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    
    private RepositorySample repository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, RepositorySample repository) {
        super(authenticationManager);
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthorizationFilter 인가: 진입");

        String header = request.getHeader(JwtProperties.HEADER_STRING);

        // JWT 헤더 토큰이 존재할 때, 토큰 검증을 실행
        if (header != null && header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            System.out.println("Header: " + header);
            String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

            // 토큰 자체 검증
            // 토큰 자체에 인가 정보가 담겨있기 때문에 AuthenticationManager가 필요 없음
            // 내가 가지고 있는 secretKey를 이용해 JWT 토큰이 올바른지 확인
            String name = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token).getClaim("name").asString();

            if (name != null) {
                EntitySample user = repository.findByName(name).orElseThrow();

                // 인증은 토큰 검증할 때 끝남, 인증을 위헤서가 아닌 스프링 시큐리티가 수행하는 권한 처리를 위해
                // 토큰을 발행해서 Authentication 객체를 강제로 만들어서 세션에 저장
                
            }
        }
    }
    

}
