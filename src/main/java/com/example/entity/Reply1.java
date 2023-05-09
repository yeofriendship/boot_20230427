package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "REPLY1")
@SequenceGenerator(name = "SEQ_R1", sequenceName = "SEQ_REPLY1_NO", initialValue = 1, allocationSize = 1)
public class Reply1 {
    // 답글번호 => 기본키, 시퀀스 사용
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_R1")
    private long no; // 답글번호

    @Lob
    private String content; // 답글내용

    private String writer; // 답글 장성자

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;

    // 답글:게시글 = n:1
    @ManyToOne
    @JoinColumn(name = "BRDNO", referencedColumnName = "NO")
    private Board1 board1;
}
