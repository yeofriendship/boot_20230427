package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController{
    // 127.0.0.1:9090/ROOT/login.do
    @GetMapping(value = "/login.do")
    public String loginGET() {
        return "login"; // login.html
    }

    // 자동 import 단축키 shift + alt + o
    //127.0.0.1:9090/ROOT/home.do --> properties에 가서 ROOT라고 설정 해줬음
    @GetMapping(value = {"/home.do", "/"}) 
    public String homeGET(Model model, @AuthenticationPrincipal User user) {  
        if (user != null) { // 로그인되었음
            System.out.println(user.toString());
        }

        //request.setAttribute("key", "value")
        model.addAttribute("user", user);

        return "home";

    }

    //127.0.0.1:9090/ROOT/403page.do
    @GetMapping(value = "/403page.do")
    public String PageGET() {
        return "/error/403page";
    }
}