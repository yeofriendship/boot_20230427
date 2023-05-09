package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Board1;
import com.example.repository.Board1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/board1")
@RequiredArgsConstructor
public class Board1Controller {
    final String format = "Board1Controller => {}";
    final Board1Repository b1Repository;

    @GetMapping(value = "/insert.do")
    public String insertGET() {
        return "/board1/insert";
    }

    @PostMapping(value = "/insert.do")
    public String insertPOST(@ModelAttribute Board1 board1) {
        log.info(format, board1.toString());

        b1Repository.save(board1);

        return "redirect:/board1/selectlist.do";
    }

    // 글번호 기준으로 내림차순 -> 전체 게시글 조회
    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model) {
        List<Board1> list = b1Repository.findAllByOrderByNoDesc();

        model.addAttribute("list", list);

        return "/board1/selectlist";
    }

    @GetMapping(value = "/selectone.do")
    public String selectoneGET(Model model, @RequestParam(name = "no") long no) {
        Board1 board1 = b1Repository.findById(no).orElse(null);
        // log.info(format, board1.toString());

        model.addAttribute("board1", board1);
        return "/board1/selectone";
    }

}
