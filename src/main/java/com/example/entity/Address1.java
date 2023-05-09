package com.example.entity;

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

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "ADDRESS1")
@SequenceGenerator(name = "SEQ_A1", sequenceName = "SEQ_ADDRESS1_NO", initialValue = 1, allocationSize = 1)
public class Address1 {
    // 주소번호 : 기본키, 시퀀스
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_A1")
    @Column(name = "NO")
    private long no;

    // 우편번호
    @Column(name = "POSTCODE", length = 10)
    private String postcode;

    // 주소 (생략시 컬럼명 변수명과 같고 길이는 255)
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp // 변경시에도 날짜 정보 변경
    private Date regdate;

    // 외래키 (생성되는 컬럼은 MEMID, 참조하는 컬럼은 MEMBER1 테이블의 ID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMID", referencedColumnName = "ID")
    private Member1 member1;
}
