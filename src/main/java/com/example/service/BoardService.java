package com.example.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.Board;


//자동import 단축키는 shift + alt + o 

@Service //서비스면 서비스 컨트롤러면 컨트롤러라고 명시해줘야함
public interface BoardService {

    //게시글 등록
    public int insertBoardOne(@Param("obj") Board obj);

    //게시글 전체 조회
    public List<Board> selectBoardList();

    //게시글 1게 조회
    public Board selectBoardOne(@Param("no") long no);


    //xml을 사용한 mapper
    //게시글 수정
    public int updateBoard(Board obj);

    //xml을 사용한 mapper
    //게시글 삭제
    public int deleteBoard(long no);
    
}
