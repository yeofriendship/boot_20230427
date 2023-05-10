package com.example.controller.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CustomUser;
import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/customer")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {
    final String format = "CustomerController => {}";
    final MemberMapper memberMapper;

    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "/customer/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member member) {
        log.info(format, member.toString()); // 화면에 정확하게 표시되고 사용자가 입력한 항목을 member 객체에 저장했음을 확인함

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(); // salt 값을 자동으로 부여함
        member.setPassword(bcpe.encode(member.getPassword())); // 기존 암호를 암호화시켜서 다시 저장함

        int ret = memberMapper.insertMemberOne(member);
        if (ret == 1) {
            return "redirect:joinok.do"; // 주소창에 127.0.0.1:9090/ROOT/customer/joinok.do 입력 후 엔터키를 자동화
        }
        return "redirect:join.do"; // 실패시 회원가입 화면으로
    }

    @GetMapping(value = "/joinok.do")
    public String joinokGET() {
        return "/customer/joinok";
    }

    @GetMapping(value = "/home.do")
    public String homeGET(@RequestParam(name = "menu", required = false, defaultValue = "0") int menu,
                          @AuthenticationPrincipal User user,
                          Model model) {
        if (menu == 1) {
            // 세션에서 아이디 정보를 꺼내서 mapper에서 조회
            Member member = memberMapper.selectMemberOne1(user.getUsername());
            model.addAttribute("member", member);

            // 체크박스에 표시할 항목들
            String[] checkLabel = {"가가가", "가나다", "나나나", "다다다", "가나다"};
            model.addAttribute("checklabel", checkLabel);
        }
        
        return "/customer/home";
    }

    // @AuthenticationPrincipal User user => HttpSession httpSession => httpSession.getAttribute("user")
    @PostMapping(value = "/home.do")
    public String homePOST(@RequestParam(name = "menu") int menu,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           @AuthenticationPrincipal CustomUser user,
                           
                           @ModelAttribute Member member) {
        log.info("CustomerController menu => {}", user.toString());
        log.info("CustomerController menu => {}", menu);

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

        // 아이디 정보 가져오기 => user.getUsername();
        if (menu == 1) {
            member.setId(user.getUsername());

            log.info("CustomerController updateone => {}", member.toString());
            
            memberMapper.updateMemberOne(member);
            return "redirect:/customer/home.do?menu=1";
        }
        else if (menu == 2) {
            // 아이디 정보를 이용해서 DB에서 1명 조회
            Member obj = memberMapper.selectMemberOne1(user.getUsername());

            // 조회된 정보의 암호와 사용자가 입력한 암호를 matches로 비교
            // 비밀번호 확인 => matches(바꾸기 전 비번, 해시된 비번)
            if (bcpe.matches(member.getPassword(), obj.getPassword())) {
                member.setId(user.getUsername());
                member.setNewpassword(bcpe.encode(member.getNewpassword()));

                memberMapper.updateMemberPw(member);

                log.info("CustomerController updatepw => {}", member.toString());
            }
            return "redirect:/customer/home.do?menu=2";
        }
        else if (menu == 3) {
            // 아이디 정보를 이용해서 DB에서 1명 조회
            Member obj = memberMapper.selectMemberOne1(user.getUsername());

            // 조회된 정보와 현재 암호가 일치하는지 matches로 비교
            // 비교가 true이면 DB에서 삭제 & 로그아웃
            if (bcpe.matches(member.getPassword(), obj.getPassword())) {
                member.setId(user.getUsername());

                memberMapper.deleteMemberOne(member);

                // 컨트롤러에서 logout 처리하기
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                log.info("CustomerController => {}", auth.toString());
                if (auth != null) {
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                }
            }
            return "redirect:/customer/home.do?menu=3";
        }
        return "redirect:/customer/home.do";
    }
}
