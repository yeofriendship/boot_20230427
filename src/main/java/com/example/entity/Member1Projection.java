package com.example.entity;

// projection : 일부만 조회 (SELECT id, name, age FROM member1)
public interface Member1Projection {
    // get + 변수명()
    String getId();
    
    String getName();

    int getAge();
}
