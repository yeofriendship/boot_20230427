package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.library.Student2;
import com.example.entity.library.Student2Projection;
import com.example.repository.library.Student2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/api/student2")
@RequiredArgsConstructor
public class RestStudent2Controller {
    // 저장소 객체 생성
    final Student2Repository s2Repository;

    // 암호화 객체 생성
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

    // 컴포넌트 객체 생성
    // 로그인(토큰 발행)
    final JwtUtil2 jwtUtil2; 

    @PostMapping(value = "/insert.json")
    public Map<String, Object> insertPOST(@RequestBody Student2 obj) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            // 암호는 bcpe를 이용하여 암호화하기
            obj.setPassword(bcpe.encode(obj.getPassword()));

            log.info("{}", obj.toString());
            s2Repository.save(obj);
            retMap.put("status", 200);
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    // get은 @RequestBody를 잘 사용하지 않는다(react 같은 경우에서 호출이 안될 수도 있다)

    // 1. 이메일 중복확인
    // /ROOT/api/student2/emailcheck.json?email=이메일
    @GetMapping(value = "/emailcheck.json")
    public Map<String, Object> emailcheckGET(@RequestParam(name = "email") String email) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            // {"chk" : "1"} : 중복되는 이메일 (사용 불가능)
            // {"chk" : "0"} : 중복되지 않는 이메일 (사용 가능)
            retMap.put("chk", s2Repository.countByEmail(email));
            retMap.put("status", 200);
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
    
    // 2. 이메일이 전달되면 회원의 이름, 연락처가 반환되는
    @GetMapping(value = "/selectone.json")
    public Map<String, Object> selectoneGET(@RequestParam(name = "email") String email) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            Student2Projection ret = s2Repository.findByEmail(email);

            retMap.put("ret", ret);
            retMap.put("status", 200);
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    // 로그인
    @PostMapping(value = "/login.json")
    public Map<String, Object> loginPOST(@RequestBody Student2 student2) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            // (1) 이메일, 암호를 전송함
            log.info("{}", student2.toString());

            // (2) 이메일을 이용해서 정보를 가져옴
            Student2 retStudent2 = s2Repository.findById(student2.getEmail()).orElse(null);

            // (3)-2 암호가 일치하지 않으면 (실패시 전송할 데이터)
            retMap.put("status", 0);

            // (3)-1 암호가 일치하는지 확인
            // 전송된 hash되지 않은 암호와 DB의 hash된 암호가 일치하는지 확인
            if (bcpe.matches(student2.getPassword(), retStudent2.getPassword())) {
                // 일치하면 토큰 발행
                retMap.put("token", jwtUtil2.createJwt(retStudent2.getEmail(), retStudent2.getName()));
                
                retMap.put("status", 200);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    // 회원 탈퇴, 비밀번호 변경, 회원 정보수정 등 로그인이 되어야하는 모든 것에는 아래 작업이 필요
    // 회원 정보 수정 (원래는 put을 써야하는데 귀찮아서 post로 함)
    // 토큰을 주세요 => 검증해서 성공하면 정보수정을 진행
    // 토큰은 보통 @RequestHeader 사용
    @PostMapping(value = "/update.json")
    public Map<String, Object> updatePOST(@RequestHeader(name = "token") String token,
                                          @RequestBody Student2 student2) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            // 1. 토큰을 받아서 출력
            log.info("{}", token);
            log.info("{}", student2.toString());

            // 3-2. (실패) 
            retMap.put("status", 0);

            // 2. 토큰을 검증한다
            Student2 obj = jwtUtil2.checkJwt(token);

            if (obj != null) {
                // 3-1. (성공) 정보를 수정한다
                // (1) 이메일을 이용해서 기존 데이터 가져오기
                Student2 ret = s2Repository.findById(obj.getEmail()).orElse(null);

                // (2) ret에 필요한 정보 저장하기
                ret.setName(student2.getName());
                ret.setPhone(student2.getPhone());

                // (3) ret를 다시 저장하기
                s2Repository.save(ret);

                retMap.put("status", 200);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
}
