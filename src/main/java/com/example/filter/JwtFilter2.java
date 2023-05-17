package com.example.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.entity.library.Student2;
import com.example.restcontroller.JwtUtil2;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

// 회원정보수정, 회원탈퇴, 비밀번호변경 등 토큰이 필요한 경우에 대한 처리 (나머지 작업은 처리하면 X)
// 컨트롤러 전에 수행되는 클래스 => 토큰의 유효성을 검증하고 실패시 컨트롤러 진입 X 하는 필터
// url 주소에 따라 분류함
@Component
@RequiredArgsConstructor
public class JwtFilter2 extends OncePerRequestFilter {
    final JwtUtil2 jwtUtil2;

    // json으로 변환하는 라이브러리
    ObjectMapper objectMapper = new ObjectMapper(); 

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, Object> retMap = new HashMap<>();

            String token = request.getHeader("token");
            if (token == null) { // {"status" : "-1", "message" : "token null"} 로 반환됨
                retMap.put("status", -1);
                retMap.put("message", "token null");

                String result = objectMapper.writeValueAsString(retMap);
                response.getWriter().write(result);

                return; // 메소드 종료
            }

            if (token.length() <= 0) { // {"status" : "-1", "message" : "token is empty"} 로 반환됨
                retMap.put("status", -1);
                retMap.put("message", "token is empty");

                String result = objectMapper.writeValueAsString(retMap);
                response.getWriter().write(result);

                return; // 메소드 종료
            }

            Student2 obj = jwtUtil2.checkJwt(token);
            if (obj == null) { // {"status" : "-1", "message" : "token error"} 로 반환됨
                retMap.put("status", -1);
                retMap.put("message", "token error");

                String result = objectMapper.writeValueAsString(retMap);
                response.getWriter().write(result);

                return; // 메소드 종료
            }

            // 아래 명령어가 실행되어서 정상적인 컨트롤러로 진입 가능
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendError(01, "token error");
        }
    }
    
}
