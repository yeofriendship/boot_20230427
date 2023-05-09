package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.ToString;

@Data
@Entity
@Table(name = "BOARDIMAGE1")
@SequenceGenerator(name = "SEQ_BOARDIMAGE1_NO", sequenceName = "SEQ_BOARDIMAGE1_NO", initialValue = 1, allocationSize = 1)
public class BoardImage1 {
    // 이미지 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARDIMAGE1_NO")
    private long no;

    // 이미지명
    private String imageName;

    // 이미지 타입
    private String imageType;

    // 이미지 사이즈
    private long imageSize;

    // 이미지 데이터
    @Lob
    @ToString.Exclude
    private byte[] imageData;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", insertable = true, updatable = false)
    private Date regdate;

    // 외래키 (게시판 테이블의 게시글번호 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRDNO", referencedColumnName = "NO")
    private Board1 board1;
}
