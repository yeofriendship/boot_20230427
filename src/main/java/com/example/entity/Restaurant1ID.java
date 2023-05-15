package com.example.entity;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 복합키로 사용할 변수 설정
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant1ID implements Serializable {
    private BigInteger no;
    private String phone;
}
