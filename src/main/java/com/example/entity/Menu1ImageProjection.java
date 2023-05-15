package com.example.entity;

import java.math.BigInteger;

// Menu1 테이블의 데이터 중에서 일부만 가져오기
public interface Menu1ImageProjection {
    // 이미지 데이터 (blob)
    byte[] getImagedata();

    // 이미지 타입
    String getImagetype();

    // 이미지 사이즈
    BigInteger getImagesize();
}
