package com.example.repository.library;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.library.Checkout2;

@Repository
public interface Checkout2Repository extends JpaRepository<Checkout2, BigInteger>  {
    
}
