package com.example.entity;

public interface Address1Projection {
    long getNo(); // 주소번호

    String getAddress(); // 주소

    Member1 getMember1(); // 회원 정보 (외래키 -> 아래에 다시 한번 interface)

    interface Member1 {
        String getId(); // 회원 아이디
        String getName(); // 회원 이름
    }

    // 조합 (주소번호와 주소정보 합치기)
    default String getNoAddress() {
        return getNo() + ", " + getAddress();
    }
}
