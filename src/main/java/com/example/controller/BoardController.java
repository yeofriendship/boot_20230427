package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Board;
import com.example.service.BoardService;

import lombok.RequiredArgsConstructor;


//단축키
// import :  shift + alt + o

@Controller
@RequestMapping(value = "/board") // 여기에 공통 주소 작성
@RequiredArgsConstructor
public class BoardController {

    // final BoardService bService; // @Autowired BoardService bService;랑 똑같은거임 편한걸로 쓰면됨 
    @Autowired BoardService bService;



    //게시글 작성
    // 127.0.0.1:9090/ROOT/board/isnert.do
    @GetMapping(value = "/insert.do")
    public String insertGET() {
        return "/board/insert";
    }


    //게시글 작성
    @PostMapping(value = "/insert.do")
    public String insertPost(@ModelAttribute Board board){ 

        System.out.println(board.toString()); 

        //DB에 추가 
        int ret = bService.insertBoardOne(board);

        if(ret == 1){ //성공시
            //적절한 페이지로 이동(게시판 목록으로)
            return "redirect:select.do";
        } 
        //실패시
        return "redirect:insert.do";

    }


/* -------------------------------------------------------------------------- */


    //게시글 전체조회
    // 127.0.0.1:9090/ROOT/board/select.do
    @GetMapping(value = "/select.do")
    public String selectGET(Model model) { //메소드 호출 

        List<Board> list = bService.selectBoardList(); 
        model.addAttribute("list", list);


        //templates에서 board폴더 생성하고 select.html 생성
        return "/board/select"; 
    }



/* -------------------------------------------------------------------------- */
    
    
    //게시글 1개 조회
    @GetMapping(value = "/selectone.do")
    public String selectBoardOneGET(@RequestParam(name = "no", defaultValue = "0", required = false) long no, Model model){  

        System.out.println(no);

        if(no == 0){
            return "redirect:select.do"; //no값이 0일 때 목록 페이지로 이동
        }

        Board obj = bService.selectBoardOne(no);
        System.out.println(obj.toString());

        model.addAttribute("obj", obj);
        //model.addAttribute( obj); //key를 생략했음, 변수명 obj가 key값임
        return "/board/selectone"; // board폴더에 selectone.html 생성하기
    }



/* -------------------------------------------------------------------------- */


    //게시글 1개 수정
    @GetMapping(value = "/update.do")
    public String updateBoardGET(@RequestParam(name = "no", defaultValue = "0", required = false) long no, Model model){  

        System.out.println(no);

        if(no == 0){
            return "redirect:select.do"; //no값이 0일 때 목록 페이지로 이동
        }

        Board obj = bService.selectBoardOne(no);
        System.out.println(obj.toString());

        model.addAttribute("obj", obj);  
        return "/board/update"; 
    }

    // //게시글 수정
    // 127.0.0.1:9090/ROOT/board/update.do
    @PostMapping(value = "/update.do")
    public String updateBoardPost(@ModelAttribute Board board) {

        int ret = bService.updateBoard(board);

        if (ret == 1) {
            return "redirect:selectone.do?no=" + board.getNo(); // 성공시 상세 화면
        }
        return "redirect:update.do?no=" + board.getNo(); // 실패시 수정 화면
    }



    //게시글 삭제
    @PostMapping(value = "/delete.do")
    public String deleteBoardPost(@RequestParam(name="no", defaultValue = "0", required=false) long no){
        System.out.println(no);

        if(no == 0){
            return "redirect:select.do";
        }

        int ret = bService.deleteBoard(no);

        if(ret == 1){
            return "redirect:select.do";
        } 
        return "redirect:boardone.do?no="+no;

    }

}