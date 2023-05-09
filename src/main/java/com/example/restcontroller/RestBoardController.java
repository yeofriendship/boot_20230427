package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Board;
import com.example.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// => html을 표시할 수 없음
// Map, Member, Board, List => 를 반환하면 자동으로 json으로 바꿔줌
@RestController
@RequestMapping(value = "/api/board")
@RequiredArgsConstructor
@Slf4j

// GET => 조회
// POST => 추가, 로그인
// DELETE => 삭제
// PUT => 전체 수정, PATCH => 일부 수정

public class RestBoardController {
    final String format = "RestBoard => {}";
    final BoardMapper boardMapper; // 매퍼 객체 생성

    // 127.0.0.1:9090/ROOT/api/board/select.json
    @GetMapping(value = "/select.json")
    public Map<String, String> selectGET() {
        Map<String, String> retMap = new HashMap<>();
        retMap.put("result", "ok");
        return retMap;
    }

    // 127.0.0.1:9090/ROOT/api/board/selectlist.json
    @RequestMapping(value = "/selectlist.json", method = {RequestMethod.GET})
    public List<Board> RequestMethodName() {
        // List => [{}, {}, {}, ...]
        return boardMapper.selectBoardList();
    }

    // 127.0.0.1:9090/ROOT/api/board/insert.json
    // 게시판 글쓰기 => 제목, 내용, 작성자 => {"title":"a", "content":"b", "writer":"c"}
    @RequestMapping(value = "/insert.json", method = {RequestMethod.POST})
    public Map<String, Integer> insertPOST(@RequestBody Board board) {
        // 전송되는 값 확인
        log.info(format, board.toString());

        // DB에 추가하고 결과를 1 또는 0으로 반환
        int ret = boardMapper.insertBoardOne(board);

        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret);
        return retMap;
    }

    // 게시글 조회수 증가
    // 게시글 번호가 전달되면 update를 이용해서 조회수를 증가시키고 결과를 result:1 or result:0
    @PutMapping(value = "/updatehit.json")
    public Map<String, Integer> updatehitPUT(@RequestBody Board board) {
        log.info(format, board.toString());

        int ret = boardMapper.updatehit(board.getNo());

        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret);
        return retMap;
    }

    // 게시글 삭제
    @DeleteMapping(value = "/delete.json")
    public Map<String, Integer> delete(@RequestBody Board board) {
        log.info(format, board.toString());

        int ret = boardMapper.deleteBoard(board.getNo());

        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret);
        return retMap;
    }

    // 게시글 수정
    @PutMapping(value = "/update.json")
    public Map<String, Integer> update(@RequestBody Board board) {
        log.info(format, board.toString());

        int ret = boardMapper.updateBoard(board);

        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret);
        return retMap;
    }
}
