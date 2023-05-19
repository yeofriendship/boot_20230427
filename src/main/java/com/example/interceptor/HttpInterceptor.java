package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

// 인터셉터 : 컨트롤러 실행 전 후를 필터 가능
@Component
@Slf4j
public class HttpInterceptor implements HandlerInterceptor {
    // view까지 처리 끝난 후
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    // controller가 끝난 후
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        log.info("======================postHandler======================");
        log.info(request.getServletPath());
    }

    // controller로 보내기 전에 처리되는 인터셉터 (반환값이 false면 controller로 요청 안함)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("======================preHandler======================");
        log.info(request.getServletPath());
        return true; // 이 값이 false이면 controller로 진입 안됨
    }
    
}
