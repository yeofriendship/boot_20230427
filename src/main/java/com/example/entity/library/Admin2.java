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
@Table(name = "ADMIN2")

@SequenceGenerator(name = "SEQ_ADMIN2_NO", sequenceName = "SEQ_ADMIN2_NO", initialValue = 1, allocationSize = 1)
public class Admin2 {
    // 관리자 번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADMIN2_NO")
    private BigInteger no;

    // 관리자명
    private String name;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    // 도서관 코드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LCODE", referencedColumnName = "CODE")
    private Library2 library2;
}
