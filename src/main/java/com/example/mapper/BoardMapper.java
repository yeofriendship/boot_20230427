package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.dto.Board;

@Mapper
public interface BoardMapper {

    //글쓰기
	@Insert({" INSERT INTO board(title, content, writer) VALUES(#{obj.title}, #{obj.content}, #{obj.writer}) "})
	public int insertBoardOne(@Param("obj") Board obj); //성공하면 1 실패시 0
    
    //전체 목록 조회
	@Select({" SELECT * FROM board ORDER BY no DESC "})
	public List<Board> selectBoardList(); //성공하면 목록 다 보여줌~ 여러 줄 나옴

    //게시글 1개 조회
    @Select({" SELECT * FROM board WHERE no=#{no}"})
    public Board selectBoardOne(@Param("no") long no);

    //게시글 수정
    //@Update({" UPDATE board SET title=#{obj.title}, content=#{obj.content} WHERE no=#{obj.no} "}) //SQL문을 여기서 안하고 xml에서 작성
	public int updateBoard(Board obj); //설계만 여기서하고 SQL문은 xml에서 작성

    //게시글 삭제
    //SQL문 없이 => xml에서 sql문 작성
	public int deleteBoard(long no);

    // 조회수 증가
    public int updatehit(long no);
}
