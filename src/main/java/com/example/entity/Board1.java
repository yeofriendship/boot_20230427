package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "BOARD1")
@SequenceGenerator(name = "SEQ_B1", sequenceName = "SEQ_BOARD1_NO", initialValue = 1, allocationSize = 1)
public class Board1 {
    // 글번호 => 기본키, 시퀀스 사용
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_B1")
    private long no; // 글번호

    private String title; // 글제목

    // 글내용 => 데이터 타입 : clob
    @Lob
    private String content; // 글내용

    private String writer; // 작성자

    @Column(columnDefinition = "long default 1")
    private long hit=1; // 조회수

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;

    @ToString.Exclude
    @OneToMany(mappedBy = "board1", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy(value = "no desc")
    List<Reply1> list = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "board1", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy(value = "no desc")
    List<BoardImage1> list1 = new ArrayList<>();

    // 대표이미지를 보관할 변수
    @Transient // 임시변수 (컬럼이 생성되지 X, mybatis dto 개념)
    private String imageUrl;
}
