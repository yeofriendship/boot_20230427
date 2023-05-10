package com.example.controller.jpa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Reply1;
import com.example.repository.Reply1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/reply1")
@RequiredArgsConstructor
public class Reply1Controller {
    final String format = "Reply1Controller => {}";
    final Reply1Repository r1Repository;

    @PostMapping(value = "/insert.do")
    public String insertPOST(@ModelAttribute Reply1 reply1) {
        log.info(format, reply1.toString());

        r1Repository.save(reply1);

        return "redirect:/board1/selectone.do?no=" + reply1.getBoard1().getNo();
    }
}
