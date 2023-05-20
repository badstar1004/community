package com.kims.community.config.filter;

import com.kims.community.config.JwtAuthenticationProvider;
import com.kims.community.users.repository.UsersRepository;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UsersRepository usersRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        // HttpServletRequest
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // 헤더에서 토큰을  받아옴
        String token = jwtAuthenticationProvider.resolveToken(httpServletRequest);

        // 회원가입, 로그인 요청 제외
        String path = httpServletRequest.getRequestURI()
            .substring(httpServletRequest.getContextPath().length());

        if (!path.startsWith("/users/signup") && !path.startsWith("/users/signin") && !path.startsWith("/h2-console")) {
            // 토큰 유효성 확인
            if (token == null || !jwtAuthenticationProvider.validToken(token)) {
                throw new ServletException("유효하지 않은 접근입니다.");
            }

            usersRepository.findById(jwtAuthenticationProvider.getUserId(token))
                .orElseThrow(() -> new ServletException("유효하지 않은 접근입니다."));

        }

        chain.doFilter(request, response);
    }
}
