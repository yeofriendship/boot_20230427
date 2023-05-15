package com.example.repository.library;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.library.Library2;

@Repository
public interface Library2Repository extends JpaRepository<Library2, BigInteger> {
    // SELECT * FROM 테이블명 WHERE 컬럼 = ?
    // findBy + 컬럼

    // SELECT * FROM library2 ORDER BY name ASC
    List<Library2> findAllByOrderByNameAsc();

    // SELECT * FROM library2 ORDER BY phone DESC
    // 연락처별 내림차순 정렬
    List<Library2> findAllByOrderByPhoneDesc();
}
