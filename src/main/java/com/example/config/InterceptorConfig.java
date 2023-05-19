package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.interceptor.HttpInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration // 서버 구동시 자동으로 호출됨
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    final HttpInterceptor httpInterceptor; // 인터셉터 객체 생성

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor)
                .addPathPatterns("/**") // 전체 페이지 인터셉터 동작
                .excludePathPatterns("/login**", "/login/**", "/css/*"); // login 부분은 제외
    }
    
}
