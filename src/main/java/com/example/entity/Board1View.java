package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Data
@Immutable // 뷰일 경우 추가 => 조회만 가능한 엔티티
@Entity
@Table(name = "BOARD1_VIEW")
public class Board1View {
    @Id
    @Column(name = "NO")
    private Long no = 0L;

    @Column(name = "TITLE")
    private String title = null;
}
