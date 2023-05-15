package com.example.controller.jpa;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Menu1;
import com.example.entity.Menu1ImageProjection;
import com.example.repository.Menu1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/menu1")
@RequiredArgsConstructor
public class Menu1Controller {
    final Menu1Repository m1Repository;

    final ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}") String defaultImage;

    @GetMapping(value = "/insert.food")
    public String insertGET(Model model,
                            @RequestParam(name = "rno") long rno,
                            @RequestParam(name = "rphone") String rphone) {
        try {
            List<Menu1> list = m1Repository.findByRestaurant1_phone(rphone);

            model.addAttribute("rno", rno);
            model.addAttribute("rphone", rphone);
            model.addAttribute("list", list);
            return "/menu1/insert";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/insert.food")
    public String insertPOST(@ModelAttribute Menu1 obj,
                             @RequestParam(name = "tmpFile") MultipartFile file) {
        try {
            // 파일은 수동으로 obj에 추가하기
            obj.setImagedata(file.getInputStream().readAllBytes());
            obj.setImagesize(BigInteger.valueOf(file.getSize()));
            obj.setImagetype(file.getContentType());
            obj.setImagename(file.getOriginalFilename());

            log.info("Menu1 => {}", obj.toString());

            m1Repository.save(obj);

            return "redirect:/menu1/insert.food?rno=" + obj.getRestaurant1().getNo() + "&rphone=" + obj.getRestaurant1().getPhone();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 이미지는 DB에서 꺼내서 url형태로 변경시켜야 함
    // 127.0.0.1:9090/ROOT/menu1/image?no=
    // <img src="/ROOT/menu1/image?no=???" />
    @GetMapping(value="/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") long no) throws IOException {
        Menu1ImageProjection obj = m1Repository.findByNo(BigInteger.valueOf(no));
        
        // import org.springframework.http.HttpHeaders;
        HttpHeaders headers = new HttpHeaders();

        if (obj != null) { // 이미지가 존재할 경우
            if (obj.getImagesize().longValue() > 0) {
                headers.setContentType(MediaType.parseMediaType(obj.getImagetype()));
                return new ResponseEntity<>(obj.getImagedata(), headers, HttpStatus.OK);
            }
        }

        // 이미지가 없을 경우
        InputStream is = resourceLoader.getResource(defaultImage).getInputStream(); // exception 발생됨
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/delete.food")
    public String deletePOST(@RequestParam(name = "no") long no,
                             @RequestParam(name = "rno") long rno,
                             @RequestParam(name = "rphone") String rphone) {
        try {
             // log.info("{}", no);

            m1Repository.deleteById(BigInteger.valueOf(no));

            return "redirect:/menu1/insert.food?rno=" + rno + "&rphone=" + rphone;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/update.food")
    public String updateGET(Model model,
                            @RequestParam(name = "no") long no,
                            @RequestParam(name = "rno") long rno,
                            @RequestParam(name = "rphone") String rphone) {
        try {
            Menu1 obj = m1Repository.findById(BigInteger.valueOf(no)).orElse(null);

            model.addAttribute("obj", obj);
            model.addAttribute("rno", rno);
            model.addAttribute("rphone", rphone);
            return "/menu1/update";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/update.food")
    public String updatePOST(@ModelAttribute Menu1 menu1,
                             @RequestParam(name = "tmpFile") MultipartFile file) {
        try {
            // log.info(menu1.toString());
            log.info(file.toString());

            // 기존 데이터를 읽어서 필요한 부분 변경 후 다시 저장하기
            // (1) 기존 데이터 읽기
            Menu1 obj = m1Repository.findById(menu1.getNo()).orElse(null);
            
            // (2) 필요한 부분 변경(저장하면 자동으로 DB에 변경됨)
            obj.setName(menu1.getName());
            obj.setPrice(menu1.getPrice());

            // null이 아니라 isEmpty() 쓰는 이유
            // => 사진 첨부를 하지 않아도 파일 객체는 만들어져 있기 때문에 (파일 첨부 안했을때에도 log.info(file.toString()) 결과값 이상한 말 줄줄~)
            //    널인지 아닌지로 구분하지 않고 isEmpty()로 구분한다.
            if (file.isEmpty() == false) { // 이미지 첨부가 되었을때 (첨부하지 않으면 기존 이미지 그대로 유지)
                obj.setImagedata(file.getInputStream().readAllBytes());
                obj.setImagename(file.getOriginalFilename());
                obj.setImagesize(BigInteger.valueOf(file.getSize()));
                obj.setImagetype(file.getContentType());
            }

            // (3) 다시 저장하기
            m1Repository.save(obj);

            return "redirect:/menu1/insert.food?rno=" + menu1.getRestaurant1().getNo() + "&rphone=" + menu1.getRestaurant1().getPhone();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
}
