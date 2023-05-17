package com.example.restcontroller;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import com.example.entity.library.Student2;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// 컨트롤러 x, 서비스 x, 엔티티 x => @Component
@Component
public class JwtUtil2 {
    private final String BASEKEY = "adfjasdklfjakdfsdafadfasdfadsfasdfazsfasdfawdfasdfasdfawsdfjhasdkjgjoraejtglkaerjfgtkljthwjaklfjkadafadsfa";

    // 토큰 생성하는 메소드
    public String createJwt(String id, String name) throws Exception {
        // 1. header 정보
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT"); // 타입
        headerMap.put("alg", "HS256"); // hash 알고리즘

        // 2. 토큰에 포함시킬 사용자 정보들
        Map<String, Object> claimsMap = new HashMap<>();
        // 토큰에 들어갈 내용
        claimsMap.put("id", id); // 아이디
        claimsMap.put("name", name); // 이름

        // 1000 * 60 * 60 * 2 : 2시간
        // 3. 토큰의 만료 시간 (현재 시간 + 1000 * 60 * 60 * 2)
        Date expiredTime = new Date();
        expiredTime.setTime(expiredTime.getTime() + 1000 * 60 * 60 * 8);

        // 4. 키 발행
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(BASEKEY);
        Key signKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        // 1 ~ 4의 정보를 이용해서 토큰 생성
        JwtBuilder builder =  Jwts.builder()
                                  .setHeader(headerMap)
                                  .setClaims(claimsMap)
                                  .setSubject("TEST")
                                  .setExpiration(expiredTime)
                                  .signWith(signKey, SignatureAlgorithm.HS256);
    
        // 토큰을 String 타입으로 변환
        return builder.compact();
    }

    // 토큰에 대해서 검증하고 데이터를 추출하는 메소드
    public Student2 checkJwt(String token) throws Exception {
        try {
            // 1. key 준비
            byte[] keyBytes = DatatypeConverter.parseBase64Binary(BASEKEY);

            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(keyBytes)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
            
            Student2 obj = new Student2();
            obj.setEmail((String) claims.get("id"));
            obj.setName((String) claims.get("name"));

            // System.out.println("추출한 이메일 => " + obj.getEmail());
            // System.out.println("추출한 이름 => " + obj.getName());

            return obj;
        }
        catch (ExpiredJwtException e1) { // 더 구체적인(상세한) 오류가 보다 더 위에 위치해야함
            System.err.println("만료시간 종료" + e1.getMessage());
            return null;
        }
        catch (JwtException e2) {
            System.err.println("토큰 오류" + e2.getMessage());
            return null;
        }
        catch (Exception e) {
            System.out.println("e1과 e2 오류가 아닌 모든 오류" + e.getMessage());
            return null;
        }
    }
}
