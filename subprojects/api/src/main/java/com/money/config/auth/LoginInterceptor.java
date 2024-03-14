package com.money.config.auth;

import com.money.config.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final TokenProvider tokenProvider;

    public LoginInterceptor(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isPreflight(request) || isSwaggerRequest(request)) {
            return true;
        }

        String token = AuthorizationExtractor.extractAccessToken(request);
        tokenProvider.validateToken(token);
        return true;
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("swagger") || uri.contains("api-docs") || uri.contains("webjars");
    }

    private boolean isPreflight(HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.OPTIONS.toString());
    }
}
