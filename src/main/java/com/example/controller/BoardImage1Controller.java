package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.example.entity.Board1;
import com.example.entity.BoardImage1;
import com.example.repository.Board1Repository;
import com.example.repository.BoardImage1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/boardimage1")
@RequiredArgsConstructor
@Slf4j
public class BoardImage1Controller {
    final String format = "BoardImage1Controller => {}";
    final Board1Repository b1Repository;
    final BoardImage1Repository bi1Repository;

    final ResourceLoader resourceLoader; // resources 폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}") String defaultImage;

    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model, HttpServletRequest request, @RequestParam(name = "no") long no) {
        try {
            // 게시글 정보
            Board1 board1 = b1Repository.findById(no).orElse(null);

            // 대표 이미지
            BoardImage1 image1 = bi1Repository.findTop1ByBoard1_noOrderByNoAsc(no);
            board1.setImageUrl(request.getContextPath() + "/boardimage1/image?no=0");
            if (image1 != null) {
                board1.setImageUrl(request.getContextPath() + "/boardimage1/image?no=" + image1.getNo());
                // log.info(format, image1.toString());
            }

            // 이미지도 포함하여 view로 전달
            model.addAttribute("board1", board1);

            // 전체 이미지
            // List<String> 타입으로 만들어서 view로 전달 후 출력
            List<String> imageList = new ArrayList<>();
            List<BoardImage1> list1 = bi1Repository.findByBoard1_noOrderByNoAsc(no);
            if (!list1.isEmpty()) {
                // log.info(format, list1.toString());
                for (BoardImage1 tmp : list1) {
                    imageList.add(request.getContextPath() + "/boardimage1/image?no=" + tmp.getNo());
                }
            }

            // 전체 이미지 view로 전달
            model.addAttribute("imageList", imageList);

            return "/boardimage1/selectlist";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/insertimage.do")
    public String insertImagePOST(@ModelAttribute BoardImage1 image1,
                                  @RequestParam(name = "tmpfile") MultipartFile file) {
        try {
            image1.setImageSize(file.getSize());
            image1.setImageData(file.getInputStream().readAllBytes());
            image1.setImageType(file.getContentType());
            image1.setImageName(file.getOriginalFilename());
            // log.info(format, image1.toString());

            bi1Repository.save(image1);

            return "redirect:/boardimage1/selectlist.do?no=" + image1.getBoard1().getNo();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    // 127.0.0.1:9090/ROOT/boardimage1/image?no=1
    @GetMapping(value="/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") long no) throws IOException {
        BoardImage1 obj = bi1Repository.findById(no).orElse(null);

        // import org.springframework.http.HttpHeaders;
        HttpHeaders headers = new HttpHeaders();

        if (obj != null) { // 이미지가 존재할 경우
            if (obj.getImageSize() > 0) {
                headers.setContentType(MediaType.parseMediaType(obj.getImageType()));
                return new ResponseEntity<>(obj.getImageData(), headers, HttpStatus.OK);
            }
        }

        // 이미지가 없을 경우
        InputStream is = resourceLoader.getResource(defaultImage).getInputStream(); // exception 발생됨
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
    }
}
