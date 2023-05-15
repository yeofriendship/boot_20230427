package com.example.repository.library;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.library.Book2;

@Repository
public interface Book2Repository extends JpaRepository<Book2, BigInteger> {
    
}
