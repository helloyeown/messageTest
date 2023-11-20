package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.jwt.JwtAuthenticationFilter;
import com.example.demo.jwt.JwtAuthorizationFilter;
import com.example.demo.repository.RepositorySample;
import com.example.demo.service.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity    // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final RepositorySample repository;
    private final AuthenticationManagerBuilder auth;
    private final JwtTokenProvider provider;

    // CORS 설정을 위한 메소드, 다른 도메인에서의 요청을 허용
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.addExposedHeader("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
    }

    // Spring Security는 AuthenticationManager를 한 번만 생성하도록 설계되어 있음
    // 빈을 중복 생성하지 않기 위해 @Bean을 사용하지 않음
    public AuthenticationManager authenticationManager() throws Exception {
        return auth.build();
    }

    // 보안 필터 체인을 설정하는 메소드
    // CSRF 공격 방지, 세션 관리, 로그인 설정, 권한 설정 등을 정의
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return     http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(formLogin -> formLogin.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), provider))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), repository))
            .authorizeRequests(authorize -> authorize       // 인가 권한이 필요한 컨트롤러 주소
                                .antMatchers("/api/users/manage/check").hasRole("USER")
                                .antMatchers("/api/users/manage/information").hasRole("USER")
                                .anyRequest().authenticated())
            .build();
    }

    // // 사용자 정의 필터를 등록
    // // JwtAuthenticationFilter와 JwtAuthorizationFilter를 추가하므로써 JWT를 이용한 인증 및 인가를 처리
    // public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
    //     public void configure(HttpSecurity http) throws Exception {
    //         AuthenticationManager manager = http.getSharedObject(AuthenticationManager.class);
    //         http.addFilter(new JwtAuthenticationFilter(manager))
    //             .addFilter(new JwtAuthorizationFilter(manager, repository));
    //     }
    // }
}
