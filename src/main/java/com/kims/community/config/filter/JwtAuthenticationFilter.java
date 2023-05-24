package com.kims.community.config.filter;

import com.kims.community.config.JwtAuthenticationProvider;
import com.kims.community.users.repository.UsersRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/h2-console/") || requestURI.startsWith("/users/signup")
            || requestURI.startsWith("/users/signin") || requestURI.startsWith("/board/list")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 토큰을 받아옴
        String token = jwtAuthenticationProvider.resolveToken(request);

        // 토큰 유효성 확인
        if (token == null) {
            throw new ServletException("로그인 후 이용 가능합니다.");

        } else if (!jwtAuthenticationProvider.validToken(token)) {
            throw new ServletException("유효하지 않은 접근입니다.");
        }

        usersRepository.findById(jwtAuthenticationProvider.getUserId(token))
            .orElseThrow(() -> new ServletException("유효하지 않은 접근입니다."));

        filterChain.doFilter(request, response);
    }
}
