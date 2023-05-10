package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "SEQ_BOARD_NO", sequenceName = "SEQ_BOARD_NO", initialValue = 1, allocationSize = 1)
public class Board {
    // 글번호 => 기본키, 시퀀스 사용
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD_NO")
    @Column(name = "NO")
    private BigDecimal no; // 글번호

    private String title; // 글제목

    // 글내용 => 데이터 타입 : clob
    @Lob
    private String content; // 글내용

    private String writer; // 작성자

    private BigDecimal hit; // 조회수

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;
}
