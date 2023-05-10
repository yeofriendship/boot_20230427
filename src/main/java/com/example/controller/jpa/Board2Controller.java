package com.example.controller.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Board;
import com.example.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/board2")
@RequiredArgsConstructor
public class Board2Controller {
    final BoardRepository bRepository; // 저장소 객체
    final HttpSession httpSession; // 세션 객체

    // 페이지네이션 X
    // 1. 전달 값 받기 => ?page=1&type=wrier&text=ddd
    // @GetMapping(value = "/selectlist.pknu")
    // public String selectlistGET(Model model,
    //                             @RequestParam(name = "page", defaultValue = "1") int page,
    //                             @RequestParam(name = "type", defaultValue = "title") String type,
    //                             @RequestParam(name = "text", defaultValue = "") String text) {
    //     try {
    //         // 2. 타입에 따라서 다른 메소드 호출
    //         // ex) findByTitle...()
    //         List<Board> list = bRepository.findByTitleIgnoreCaseContainingOrderByNoDesc(text);
    //         if (type.equals("content")) { // (2) type=content
    //             list = bRepository.findByContentIgnoreCaseContainingOrderByNoDesc(text);
    //         }
    //         else if (type.equals("writer")) { // (3) type=writer
    //             list = bRepository.findByWriterIgnoreCaseContainingOrderByNoDesc(text);
    //         }

    //         model.addAttribute("list", list);
    //         return "/board2/selectlist";
    //     } 
    //     catch (Exception e) {
    //         e.printStackTrace();
    //         return "redirect:/home.do";
    //     }
    // }

    // 페이지네이션 O
    @GetMapping(value = "/selectlist.pknu")
    public String selectlistGET(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "type", defaultValue = "title") String type,
                                @RequestParam(name = "text", defaultValue = "") String text) {
        try {
            if (page == 0) {
                return "redirect:/board2/selectlist.pknu?page=1&type=title&text=";
            }

            PageRequest pageRequest = PageRequest.of((page-1), 10);
            
            List<Board> list = bRepository.findByTitleIgnoreCaseContainingOrderByNoDesc(text, pageRequest);
            long total = bRepository.countByTitleIgnoreCaseContainingOrderByNoDesc(text);

            if (type.equals("content")) {
                list = bRepository.findByContentIgnoreCaseContainingOrderByNoDesc(text, pageRequest);
                total = bRepository.countByContentIgnoreCaseContainingOrderByNoDesc(text);
            }
            else if (type.equals("writer")) {
                list = bRepository.findByWriterIgnoreCaseContainingOrderByNoDesc(text, pageRequest);
                total = bRepository.countByWriterIgnoreCaseContainingOrderByNoDesc(text);
            }

            model.addAttribute("list", list);
            model.addAttribute("pages", (total-1) / 10 + 1);
            return "/board2/selectlist";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @GetMapping(value = "/insertbatch.pknu")
    public String insertbatchGET() {
        try {
            return "/board2/insertbatch";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/insertbatch.pknu")
    public String insertbatchPOST(@RequestParam(name = "title[]") String[] title,
                                  @RequestParam(name = "content[]") String[] content,
                                  @RequestParam(name = "writer[]") String[] writer) {
        try {
            List<Board> list = new ArrayList<>();
            for (int i=0; i<title.length; i++) {
                Board board = new Board();
                board.setTitle(title[i]);
                board.setContent(content[i]);
                board.setWriter(writer[i]);
                board.setHit(BigDecimal.valueOf(1));

                list.add(board);
            }

            bRepository.saveAll(list);

            return "redirect:/board2/selectlist.pknu";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/deletebatch.pknu")
    public String deletebatchPOST(@RequestParam(name = "chk[]") List<BigDecimal> chk) {
        try {
            log.info(chk.toString());
            bRepository.deleteAllById(chk);
            return "redirect:/board2/selectlist.pknu";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = "/updatebatch.pknu")
    public String updatebatchGET(Model model) {
        try {
            List<BigDecimal> chk = (List<BigDecimal>) httpSession.getAttribute("chk[]");
            
            List<Board> list = bRepository.findAllById(chk);

            model.addAttribute("list", list);

            return "/board2/updatebatch";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/updatebatch.pknu")
    public String updatebatchPOST(@RequestParam(name = "chk[]") List<BigDecimal> chk) {
        try {
            httpSession.setAttribute("chk[]", chk);
            return "redirect:/board2/updatebatch.pknu";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value = "/updatebatchaction.pknu")
    public String updatePOST(@RequestParam(name = "no[]") long[] no,
                             @RequestParam(name = "title[]") String[] title,
                             @RequestParam(name = "content[]") String[] content,
                             @RequestParam(name = "writer[]") String[] writer) {
        try {
            List<Board> list = new ArrayList<>();
            for (int i=0; i<no.length; i++) {
                // 1. no를 이용하여 기존 정보 가져오기
                Board board = bRepository.findById(BigDecimal.valueOf(no[i])).orElse(null);

                // 2. 기존 정보에 위에서 받은 제목, 내용, 작성자 변경하기
                board.setTitle(title[i]);
                board.setContent(content[i]);
                board.setWriter(writer[i]);

                // 3. list에 담기
                list.add(board);
            }

            // 4. 일괄 저장하기
            bRepository.saveAll(list);
            return "redirect:/board2/selectlist.pknu";
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
}
