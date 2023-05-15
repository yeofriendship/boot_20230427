package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Restaurant1;
import com.example.entity.Restaurant1ID;

// 기본키가 복합키(no, phone)이므로
// JpaRepository<엔티티, 데이터타입> => JpaRepository<엔티티, 복합키를 정의한 엔티티>
@Repository
public interface Restaurant1Repository extends JpaRepository<Restaurant1, Restaurant1ID> {
    /* 검색 + 페이지네이션 */
    // SELECT * FROM restaurant1 WHERE phone LIKE '%' || ? || '%' ORDER BY no DESC;
    
    //전화
    List<Restaurant1> findByPhoneContainingOrderByNoDesc(String phone, Pageable pageable); 
    //가게명
    List<Restaurant1> findByNameContainingOrderByNoDesc(String name, Pageable pageable); 
    //종류
    List<Restaurant1> findByTypeOrderByNoDesc(String type, Pageable pageable); 
    //주소
    List<Restaurant1> findByAddressContainingOrderByNoDesc(String address, Pageable pageable); 

    // ----------------------------------------------------------------------------------
    /* 개수 */
    // SELECT COUNT(*) FROM restaurant1 WHERE phone LIKE '%' || ? || '%';

    //전화
    long countByPhoneContaining(String phone); 
    //가게명
    long countByNameContaining(String name); 
    //종류
    long countByTypeContaining(String type); 
    //주소
    long countByAddressContaining(String address); 

}
