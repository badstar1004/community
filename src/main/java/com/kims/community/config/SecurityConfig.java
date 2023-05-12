package com.kims.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // http 스크래치 테스트 시 외부 호출
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        // 접속 모두 허용
        http.authorizeRequests()
            .anyRequest().permitAll();

        return http.build();
    }
}
