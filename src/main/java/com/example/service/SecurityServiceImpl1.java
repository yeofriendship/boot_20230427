package com.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.library.Student2;
import com.example.repository.library.Student2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// (1) 로그인에서 로그인 버튼 -> loadUserByusername으로 이메일 정보를 넘김
// student2 테이블과 연동되는 서비스
@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityServiceImpl1 implements UserDetailsService {
    final String format = "SecurityServiceImpl => {}";
    final Student2Repository s2Repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // (2) 이메일을 이용해서 student2 테이블에서 정보를 꺼낸 후 User 타입으로 변환해서 리턴하면
        // 시큐리티가 비교 후에 로그인 처리를 자동으로 수행함
        log.info(format, username);

        Student2 obj = s2Repository.findById(username).orElse(null);

        if (obj != null) { // 이메일이 있는 경우
            return User.builder()
                       .username(obj.getEmail())
                       .password(obj.getPassword())
                       .roles("STUDENT2")
                       .build();
        }
           
        // 이메일이 없는 경우는 User로 처리
        return User.builder()
            .username("_")
            .password("_")
            .roles("_")
            .build();
    }

    
}
