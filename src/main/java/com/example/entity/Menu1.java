package com.example.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "MENU1")
@SequenceGenerator(name = "SEQ_MENU1_NO", sequenceName = "SEQ_MENU1_NO", initialValue = 1, allocationSize = 1)
public class Menu1 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MENU1_NO")
    private BigInteger no;

    private String name;

    private BigInteger price;

    // 이미지 데이터 (blob)
    @Lob
    @ToString.Exclude
    private byte[] imagedata;

    // 이미지명
    private String imagename;

    // 이미지 타입
    private String imagetype;

    // 이미지 사이즈
    private BigInteger imagesize;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", insertable = true, updatable = false)
    private Date regdate;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "rphone", referencedColumnName = "phone"),
        @JoinColumn(name = "rno", referencedColumnName = "no")
    })
    private Restaurant1 restaurant1;
}
