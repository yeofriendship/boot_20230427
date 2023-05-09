package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/member")
@RequiredArgsConstructor
@Slf4j
public class RestMemberController {
    final MemberMapper memberMapper;
    BCryptPasswordEncoder pcpe = new BCryptPasswordEncoder(); // 암호화 객체 생성
    final JwtUtil jwtUtil; // Component 객체 생성 - 토큰 발행

    // 회원가입
    @PostMapping(value = "/join.json")
    public Map<String, Integer> joinPOST() {
        int ret = 1;
        
        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret);
        return retMap;
    }

    // 로그인 => 로그인 성공시 세션에 정보를 저장하고 다른 페이지에서 확인
    // 로그인 => 토큰을 발행함(토큰에는 로그인 사용자 정보 만료시간이 포함되어 있음)
    // 127.0.0.1:9090/ROOT/api/member/login.json => {"id":"bbb", "password":"a"}
    @PostMapping(value = "/login.json")
    public Map<String, Object> loginPOST(@RequestBody Member member) {
        log.info("RestMember => {}", member.toString());

        // (1) 아이디를 이용해서 회원정보 가져오기
        Member retMember = memberMapper.selectMemberOne1(member.getId());
        
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("result", 0);

        if (retMember != null) {
            // (2) 가져온 암호와 전달된 암호가 일치하는지 확인
            if (pcpe.matches(member.getPassword(), retMember.getPassword())) {
                // (3) 암호가 일치하면 토큰 발행
                retMap.put("result", 1);
                retMap.put("token", jwtUtil.generateToken(member.getId(), "CUSTOMER"));
            }
        }

        return retMap;
    }
}
