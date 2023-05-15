package com.example.repository.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.library.Student2;
import com.example.entity.library.Student2Projection;

@Repository
public interface Student2Repository extends JpaRepository<Student2, String>  {
    // Rest 1. 이메일 중복확인
    // SELECT COUNT(*) FROM student2 WHERE email = ?
    long countByEmail(String email);

    // Rest 2. 이메일이 전달되면 회원의 이름, 연락처가 반환
    // SELECT name, phone FROM student2 WHERE email = ?
    Student2Projection findByEmail(String email);
}
