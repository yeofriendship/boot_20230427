package com.example.entity.library;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "STUDENT2")
public class Student2 {
    // 이메일
    @Id
    @Column(name = "EMAIL")
    private String email;

    // 이름
    private String name;

    // 연락처
    private String phone;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate;

    // 암호
    private String password;
    
    // 대출
    @ToString.Exclude
    @OneToMany(mappedBy = "student2", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Checkout2> checkout2 = new ArrayList<>();
}
