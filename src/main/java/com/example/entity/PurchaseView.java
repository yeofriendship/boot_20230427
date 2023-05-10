package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "PURCHASEVIEW")
public class PurchaseView {
    @Id
    @Column(name = "NO")
    private BigDecimal no;

    @Column(name = "CNT")
    private BigDecimal cnt;

    @Column(name = "ITEMNO")
    private BigDecimal itemNo;

    @Column(name = "ITEMPRICE")
    private BigDecimal itemPrice;

    @Column(name = "CUSTOMERID")
    private String customerId = null;

    @Column(name = "CUSTOMERNAME")
    private String customerName = null;

    @Column(name = "ITEMNAME")
    private String itemName = null;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "REGDATE")
    private Date regdate;
}
