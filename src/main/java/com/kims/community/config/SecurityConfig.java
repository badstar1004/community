package com.kims.community.config;

import com.kims.community.config.filter.JwtAuthenticationFilter;
import com.kims.community.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UsersRepository usersRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http

            // 접속 모두 허용
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()

            // http 스크래치 테스트 시 외부 호출
            .csrf().disable()   // CSRF 공격을 방지하기 위해서는 활성화하는게 좋음
            .headers().frameOptions().sameOrigin()
            .and()

            .addFilterBefore(
                new JwtAuthenticationFilter(jwtAuthenticationProvider, usersRepository),
                UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
