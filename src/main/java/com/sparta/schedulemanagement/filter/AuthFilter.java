package com.sparta.schedulemanagement.filter;


import com.sparta.schedulemanagement.entity.User;
import com.sparta.schedulemanagement.jwt.JwtUtil;
import com.sparta.schedulemanagement.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) && (url.startsWith("/api/users"))) {
            log.info("인증 처리를 하지 않은 URL : " + url);
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                try {
                    // JWT 토큰 substring
                    String token = jwtUtil.substringToken(tokenValue);

                    // 토큰 검증
                    jwtUtil.validateToken(token);

                    // 토큰에서 사용자 정보 가져오기
                    Claims info = jwtUtil.getUserInfoFromToken(token);

                    User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                            new NullPointerException("Not Found User")
                    );

                    request.setAttribute("user", user);
                    chain.doFilter(request, response); // 다음 Filter 로 이동
                } catch (ResponseStatusException ex) {
                    log.error("유효하지 않은 토큰입니다: " + ex.getReason());
                    httpServletResponse.sendError(ex.getStatusCode().value(), ex.getReason());
                }
            } else {
                log.error("토큰이 존재하지 않습니다.");
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not Found Token");
            }
        }

    }
}