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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "LIBRARY2")

@SequenceGenerator(name = "SEQ_LIBRARY2_CODE", sequenceName = "SEQ_LIBRARY2_CODE", initialValue = 1, allocationSize = 1)
public class Library2 {
    // 도서관 코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LIBRARY2_CODE")
    @Column(name = "CODE")
    private BigInteger code;

    // 도서관명
    private String name;

    // 위치
    private String address;

    // 연락처
    private String phone;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp()
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    // 도서
    @ToString.Exclude
    @OneToMany(mappedBy = "library2", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Book2> book2 = new ArrayList<>();

    // 관리자
    @ToString.Exclude
    @OneToMany(mappedBy = "library2", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Admin2> admin2 = new ArrayList<>();
}
