package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.library.Library2;
import com.example.repository.library.Library2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/library2")
@RequiredArgsConstructor
@Slf4j
public class RestLibrary2Controller {
    final Library2Repository l2Repository;

    // 127.0.0.1:9090/ROOT/api/library2/insert.json
    // 결과값 => {result : 1}
    // @RequestBody Library2 obj => 기본으로 보낼때
    // @ModelAttribute Library2 obj => 위의 방식이 불가능할때 ex) 이미지 첨부
    @PostMapping(value = "/insert.json")
    public Map<String, Object> insetPOST(@RequestBody Library2 obj) {
        Map<String, Object> retMap = new HashMap<>();

        try {
            log.info("{}", obj.toString());
            l2Repository.save(obj);
            retMap.put("status", 200);
        }
        catch (Exception e) {
            e.printStackTrace(); // 개발자용 debug
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }

    // 127.0.0.1:9090/ROOT/api/library2/selectlist.json
    @GetMapping(value = "/selectlist.json")
    public Map<String, Object> selectlistGET() {
        Map<String, Object> retMap = new HashMap<>();

        try {
            List<Library2> list = l2Repository.findAllByOrderByNameAsc();
            log.info("{}", list.toString());
            retMap.put("status", 200);
            retMap.put("list", list);
        }
        catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage());
        }
        return retMap;
    }
}
