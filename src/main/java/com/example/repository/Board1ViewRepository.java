package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Board1View;

@Repository
public interface Board1ViewRepository extends JpaRepository<Board1View, Long> {
    // 전체 조회(내림차순)
    List<Board1View> findAllByOrderByNoDesc();

    // 문제 1 : 글번호와 제목이 정확하게 일치하는 것만 조회
    List<Board1View> findByNoAndTitle(Long no, String title);

    // 문제 2 : 글번호와 제목 둘 중 하나 이상 일치하는 것만 조회
    List<Board1View> findByNoOrTitleOrderByNoDesc(Long no, String title);

    // 문제 3 : 글번호에 해당하는 항목만 조회
    List<Board1View> findByNoInOrderByNoDesc(List<Long> no);

    // 문제 4 : 제목이 해당하는 항목만 조회
    List<Board1View> findByTitleInOrderByNoDesc(List<String> title);
}
