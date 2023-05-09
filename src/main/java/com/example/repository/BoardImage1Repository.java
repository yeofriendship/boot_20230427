package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.BoardImage1;

@Repository
public interface BoardImage1Repository extends JpaRepository<BoardImage1, Long> {
    // 게시글번호가 일치하는 것 중에서 이미지 번호가 가작 작은 것을 반환 (대표이미지)
    // SELECT * FROM boardimage1 WHERE board1.no = 13 ORDER BY no ASC LIMIT 1;
    BoardImage1 findTop1ByBoard1_noOrderByNoAsc(Long no);

    // 게시글번호가 일치하는 모든 이미지
    // SELECT * FROM boardimage1 WHERE board1.no = 13 ORDER BY no ASC;
    List<BoardImage1> findByBoard1_noOrderByNoAsc(Long no);

    // 추가, 수정, 삭제 ...
    
    // 조회
    // 변수명이 객체인 경우 : Board1_no
    // 변수명이 객체가 아닌 경우 : No, Name, Age
    // findBy변수명OrderBy변수명Asc
}
