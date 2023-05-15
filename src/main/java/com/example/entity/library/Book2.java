package com.example.entity.library;

import java.math.BigInteger;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "BOOK2")

@SequenceGenerator(name = "SEQ_BOOK2_CODE", sequenceName = "SEQ_BOOK2_CODE", initialValue = 1, allocationSize = 1)
public class Book2 {
    // 도서 코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOOK2_CODE")
    @Column(name = "CODE")
    private BigInteger code;

    // 제목
    private String title;

    // 저자
    private String writer;

    // 가격
    private BigInteger price;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    // 도서관 코드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LCODE", referencedColumnName = "CODE")
    private Library2 library2;

    // 대출
    @ToString.Exclude
    @OneToMany(mappedBy = "book2", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Checkout2> checkout2 = new ArrayList<>();
}
