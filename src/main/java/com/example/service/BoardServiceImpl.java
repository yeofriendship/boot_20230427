package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.Board;
import com.example.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    
    //칠판에서 5번에 해당
    @Autowired BoardMapper bMapper;
    //final BoardMapper bMapper; // @Autowired BoardMapper bMapper;와 같음 둘 중에 아무거나 사용

    //게시글 작성
    @Override
    public int insertBoardOne(Board obj) {
        try {
            return bMapper.insertBoardOne(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'insertBoardOne'");
        }
    }

    //게시글 전체 조회
    @Override
    public List<Board> selectBoardList() {
        
        try {
            return bMapper.selectBoardList();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }


    //게시글 1개 조회
    @Override
    public Board selectBoardOne(long no) {
        try {
            return bMapper.selectBoardOne(no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //게시글 수정
    @Override
    public int updateBoard(Board obj) {
        try {
            return bMapper.updateBoard(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        
    }

    //게시글 삭제
    @Override
    public int deleteBoard(long no) {
        try {
            return bMapper.deleteBoard(no);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;   
        }
        
    }   
}
