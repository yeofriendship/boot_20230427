package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Item;
import com.example.mapper.ItemMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/seller")
@Slf4j
@RequiredArgsConstructor
public class SellerController {
    // 매퍼 객체 생성
    final ItemMapper iMapper;

    // 세션 객체 생성
    final HttpSession httpSession;

    // 127.0.0.1:9090/ROOT/seller/home.do
    @GetMapping(value = "/home.do")
    public String homeGET(Model model, @RequestParam(name = "menu", defaultValue = "0", required = false) int menu) {
        if (menu == 1) {   
        }
        else if (menu == 2) {
            String userid = (String) httpSession.getAttribute("USERID");
            List<Item> list = iMapper.selectItemSellerList(userid);
            model.addAttribute("list", list);
        }
        
        return "/seller/home";
    }

    // 127.0.0.1:9090/ROOT/seller/home.do?menu=1
    @PostMapping(value = "/home.do")
    public String homePOST(@RequestParam(name = "menu", defaultValue = "0", required = false) int menu,

                           @RequestParam(name = "name[]", required = false) String[] name,
                           @RequestParam(name = "content[]", required = false) String[] content,
                           @RequestParam(name = "price[]", required = false) long[] price,
                           @RequestParam(name = "quantity[]", required = false) long[] quantity,

                           @RequestParam(name = "chk[]", required = false) long[] no,
                           @RequestParam(name = "btn", required = false) String btn) {
        if (menu == 0) {
            return "redirect:home.do?menu=1";
        }
        
        if (menu == 1) { // 일괄 등록
            List<Item> list = new ArrayList<>();
            for (int i=0; i<name.length; i++) {
                Item item = new Item();
                item.setName(name[i]);
                item.setContent(content[i]);
                item.setPrice(price[i]);
                item.setQuantity(quantity[i]);
                item.setSeller((String)httpSession.getAttribute("USERID"));
                list.add(item);
            }
            log.info("seller.do => {}", list.toString());

            int ret = iMapper.insertItemBatch(list);
            log.info("seller.do => {}", ret);
        }
        else if (menu == 2) {
            log.info("seller.do => {}, {}", no, btn);
            if (btn.equals("일괄삭제")) {
                iMapper.deleteItemBatch(no);
            }
            else if (btn.equals("일괄수정")) {
                // 체크한 항목 정보를 가지고 있음
                // redirect => get으로 이동 후에 화면 표시
                httpSession.setAttribute("chk[]", no);
                return "redirect:updatebatch.do";
            }
        }
        return "redirect:home.do?menu=" + menu;
    }

    @GetMapping(value = "/updatebatch.do")
    public String updateBatchGET(Model model) {
        long[] no = (long[]) httpSession.getAttribute("chk[]");
        log.info("updatebatch.do[GET] => {}", no);

        List<Item> list = iMapper.selectItemNoList(no);
        log.info("updatebatch.do[GET] => {}", list.toString());
        model.addAttribute("list", list);

        return "/item/updatebatch";
    }

    @PostMapping(value = "/updatebatch.do")
    public String updateBatchPOST(
                    @RequestParam(name = "no[]") long[] no,
                    @RequestParam(name = "name[]") String[] name,
                    @RequestParam(name = "content[]") String[] content,
                    @RequestParam(name = "price[]") long[] price,
                    @RequestParam(name = "quantity[]") long[] quantity) {
        
        List<Item> list = new ArrayList<>();
        
        for (int i=0; i<no.length; i++) {
            Item item = new Item();
            item.setNo(no[i]);
            item.setName(name[i]);
            item.setContent(content[i]);
            item.setPrice(price[i]);
            item.setQuantity(quantity[i]);

            list.add(item);
        }
        log.info("updatebatch.do[POST] => {}", list.toString());

        int ret = iMapper.updateItemBatch(list);
        return "redirect:/seller/home.do?menu=2";
    }
}
