package com.example.repository.library;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.library.Admin2;

@Repository
public interface Admin2Repository extends JpaRepository<Admin2, BigInteger>  {
    
}
