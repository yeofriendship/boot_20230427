package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.dto.Item;
import com.example.dto.ItemImage;
import com.example.mapper.ItemMapper;
import com.example.service.ItemService;

@Controller
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired ItemService iService; // 서비스 객체 생성
    @Autowired ItemMapper iMapper; // 서비스를 사용하지 않고 매퍼 호출(일반적이지 않다)

    @Autowired ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성

    @Value("${default.image}") String defaultImage;

    // 127.0.0.1:9090/ROOT/item/selectlist.do
    @GetMapping(value = "/selectlist.do")
    public String selectListGET(Model model){
        // 1. 서비스를 호출하여 물품 목록 받기
        List<Item> list = iService.selectItemList();

        // 2. model을 이용하여 view로 받은 목록 전달하기
        model.addAttribute("list", list);

        // 3. view를 화면에 표시하기
        return "/item/selectlist"; // resources/templates 위치에 item 폴더를 생성 -> selectlist.html을 생성
    }

    // /item/insertimage.do?no=7 => name 값은 no이고 value 값은 숫자 7이 전달됨
    // <input type="text" name="no" value="7" />
    @GetMapping(value = "/insertimage.do")
    public String insertimageGET(@RequestParam(name = "no", defaultValue = "0", required = false) long no, Model model) {
        if (no == 0) {
            return "redirect:selectlist.do"; // 상대경로로 이동(현재 주소에서 가장 마지막 주소만 변경해서 이동함)
        }

        // 현재 물품에 해당하는 이미지 번호
        List<Long> imgNo = iMapper.selectItemImageNo(no);
        System.out.println("insertimage.do => " + imgNo.toString());
        model.addAttribute("imgno", imgNo);
        model.addAttribute("itemno", no);

        return "/item/insertimage"; // resources/templates/item/insertimage.html
    }

    // 파일은 dto에 자동으로 추가되지 않음. 수동으로 추가
    @PostMapping(value = "/insertimage.do")
    public String insertImagePOST(@ModelAttribute ItemImage obj,
                                  @RequestParam(name = "file1") MultipartFile file1) throws IOException {
                                      
        obj.setFilename(file1.getOriginalFilename());
        obj.setFilesize(file1.getSize());
        obj.setFiletype(file1.getContentType());
        obj.setFiledata(file1.getBytes()); // exception 발생됨

        System.out.println(obj.toString()); // 확인용
        
        int ret = iService.insertItemImageOne(obj);
        if (ret == 1){
            return "redirect:insertimage.do?no=" + obj.getItemno();
        }
        
        return "redirect:insertimage.do?no=" + obj.getItemno();
    }

    // 127.0.0.1:9090/ROOT/item/image?no=1
    // <img src="@{/item/image(no=1)}">
    // 반환타입
    // (1) String : html 파일을 표시
    // (2) ResponseEntity<byte[]> : 이미지, 동영상 등을 표시
    @GetMapping(value="/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") long no) throws IOException {
        ItemImage obj = iMapper.selectItemImageOne(no);

        // import org.springframework.http.HttpHeaders;
        HttpHeaders headers = new HttpHeaders();

        if (obj != null) { // 이미지가 존재할 경우
            if (obj.getFilesize() > 0) {
                headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));
                return new ResponseEntity<>(obj.getFiledata(), headers, HttpStatus.OK);
            }
        }

        // 이미지가 없을 경우
        InputStream is = resourceLoader.getResource(defaultImage).getInputStream(); // exception 발생됨
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }
}
