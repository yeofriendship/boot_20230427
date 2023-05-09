package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Reply1;

@Repository
public interface Reply1Repository extends JpaRepository<Reply1, Long> {
    
}
