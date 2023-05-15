package com.example.entity.library;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "CHECKOUT2")

@SequenceGenerator(name = "SEQ_CHECKOUT2_CODE", sequenceName = "SEQ_CHECKOUT2_CODE", initialValue = 1, allocationSize = 1)
public class Checkout2 {
    // 대출코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHECKOUT2_CODE")
    private BigInteger code;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    // 도서 코드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BCODE", referencedColumnName = "CODE")
    private Book2 book2;

    // 학생 이메일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEMAIL", referencedColumnName = "EMAIL")
    private Student2 student2;
}

