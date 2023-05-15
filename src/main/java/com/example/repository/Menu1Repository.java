package com.example.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Menu1;
import com.example.entity.Menu1ImageProjection;

@Repository
public interface Menu1Repository extends JpaRepository<Menu1, BigInteger> {
    // findBy변수명_하위변수
    List<Menu1> findByRestaurant1_phone(String phone);

    // Menu1ImageProjection - 이미지만 꺼냄
    // 메뉴 번호가 전달되면 이미지 정보 반환(데이터, 타입, 사이즈)
    Menu1ImageProjection findByNo(BigInteger no);
}
