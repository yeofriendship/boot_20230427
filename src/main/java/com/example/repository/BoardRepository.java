package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, BigDecimal> {
    // SELECT * FROM board ORDER BY no DESC
    List<Board> findAllByOrderByNoDesc();

    // IgnoreCase : 대소문자 구분 X
    // Board2Controller - getmapping(/selectlist.pknu) => 3. 검색어 타입에 따른 메소드 3개 만들기
    // SELECT * FROM board WHERE title LIKE '%' || ? || '%' ORDER BY no DESC
    // (1) type=title
    List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc(String title);

    // SELECT * FROM board WHERE content LIKE '%' || ? || '%' ORDER BY no DESC
    // (2) type=content
    List<Board> findByContentIgnoreCaseContainingOrderByNoDesc(String content);

    // SELECT * FROM board WHERE writer LIKE '%' || ? || '%' ORDER BY no DESC
    // (3) type=writer
    List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc(String writer);

    // 검색어 + 페이지네이션
    List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc(String title, Pageable pageable);
    List<Board> findByContentIgnoreCaseContainingOrderByNoDesc(String content, Pageable pageable);
    List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc(String writer, Pageable pageable);
    
    long countByTitleIgnoreCaseContainingOrderByNoDesc(String title);
    long countByContentIgnoreCaseContainingOrderByNoDesc(String content);
    long countByWriterIgnoreCaseContainingOrderByNoDesc(String writer);
}
