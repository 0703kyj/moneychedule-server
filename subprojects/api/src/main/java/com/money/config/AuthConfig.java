package com.money.config;

import com.money.config.auth.AuthenticationPrincipalArgumentResolver;
import com.money.config.auth.LoginInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/members/**","/diaries/**")
                .excludePathPatterns("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs", "/error")
                .excludePathPatterns("/members", "/members/oauth");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
