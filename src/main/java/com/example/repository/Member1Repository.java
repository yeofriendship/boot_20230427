package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Member1;
import com.example.entity.Member1Projection;

@Repository
// JpaRepository<Member1, String> => JpaRepository<엔티티 , 엔티티의 기본키 타입>
public interface Member1Repository extends JpaRepository<Member1, String> {
    // JPQL => select * from member1 order by name desc; 
    public List<Member1> findAllByOrderByNameDesc();

    // JPQL => select * from member1 where name like '%?%' order by name desc;
    public List<Member1> findByNameContainingOrderByNameDesc(String name);

    public long countByNameContaining(String name);

    // h2 db의 문제로 사용 불가 -> nativeQuery 사용
    // JPQL => select * from member1 where name like '%?%' order by name desc limit 페이지네이션;
    public List<Member1> findByNameContainingOrderByNameDesc(String name, Pageable pageable);

    // mybatis mapper와 같음 => #{name}
    // nativequery사용하기 => :name
    @Query(value="SELECT * FROM ( SELECT m1.*, ROW_NUMBER() OVER (ORDER BY name DESC) rown FROM MEMBER1 m1 WHERE m1.name LIKE '%' || :name || '%' ) WHERE rown BETWEEN :start AND :end", nativeQuery=true)
    public List<Member1> selectByNameContainingPagenation(@Param("name") String name, @Param("start") int start, @Param("end") int end );

    // ----------------------------------------------------------------------------------------
    // projection
    
    // SELECT id, name, age FROM member1 ORDER BY id ASC;
    public List<Member1Projection> findAllByOrderByIdAsc();
}
