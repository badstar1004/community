package com.kims.community.config;

import com.kims.community.config.filter.JwtAuthenticationFilter;
import com.kims.community.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UsersRepository usersRepository;

    /**
     * 비밀번호 암호화 Bean
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 접속 모두 허용
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .csrf().disable()   // CSRF 공격을 방지하기 위해서는 활성화하는게 좋음
            .headers().frameOptions().sameOrigin()
            .and()
            .addFilterAfter(new JwtAuthenticationFilter(jwtAuthenticationProvider, usersRepository)
            , FilterSecurityInterceptor.class);

        return http.build();
    }
}
