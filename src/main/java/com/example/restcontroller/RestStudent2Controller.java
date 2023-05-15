package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    final Student2Repository s2Repository;

    // 암호화
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

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
}
