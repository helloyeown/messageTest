package com.example.demo.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.model.dto.LoginRequest;
import com.example.demo.service.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

// 인증 기능
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager;
    private final JwtTokenProvider provider;

    public JwtAuthenticationFilter(AuthenticationManager manager, JwtTokenProvider provider) {
        this.manager = manager;
        this.provider = provider;
        super.setAuthenticationManager(manager);
    }

    // AuthenticationManager를 통해 Authentication을 리턴해야 함
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter 로그인 : 진입");

        // JSON 형태의 Http 요청 본문 -> 자바 객체 변환
        ObjectMapper om = new ObjectMapper();
        LoginRequest loginRequest = null;

        try {
            loginRequest = om.readValue(request.getInputStream(), LoginRequest.class);
        } catch (Exception e) {
            throw new AuthenticationException("로그인 요청 데이터 변환 중 오류가 발생했습니다."){};
        }

        // 사용자 이름만 인증할 것이기 때문에 credentials는 null로 비워둠
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getName(), null);

        // manager가 등록된 AuthenticationProvider 중에서 supports 메서드가 true를 반환하는 프로바이더를 선택하여 인증을 수행
        Authentication authentication = manager.authenticate(token);

        return authentication;
    }

    // Authentication에 있는 정보로 jwt 토큰 생성, 헤더에 담아 반환
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
                
        String name = ((UserDetails) authResult.getPrincipal()).getUsername();      // 인증된 사용자의 이름 얻어옴
        String token = provider.createToken(name);

        response.addHeader("Authorization", "Bearer " + token);
    }

}
