package com.example.demo.jwt;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.demo.model.entity.EntitySample;
import com.example.demo.repository.RepositorySample;

import lombok.RequiredArgsConstructor;

// 비밀번호 없이 이름으로만 인증하기 때문에 커스텀 프로바이더를 만들어줌
@Component
@RequiredArgsConstructor
public class CustomAuthenticProvider implements AuthenticationProvider {

    private final RepositorySample repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();

        EntitySample user = repository.findByName(name).orElseThrow(() -> new BadCredentialsException("Invalid Name"));

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }

    // UsernamePasswordAuthenticationToken를 지원하도록 설정
    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
    }
}
