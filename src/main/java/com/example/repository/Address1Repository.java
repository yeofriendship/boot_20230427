package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Address1;
import com.example.entity.Address1Projection;


// 저장소 생성 : JpaRepository에는 기본적인 crud 구현되어 있음
// JpaRepository<엔티티, 기본키의 데이터 타입>
@Repository
public interface Address1Repository extends JpaRepository<Address1, Long> {
    // SELECT * FROM address1 WHERE address=?
    List<Address1> findByAddress(String address);

    // SELECT * FROM address1 WHERE postcode=?
    List<Address1> findByPostcode(String postcode);

    // SELECT * FROM address1 WHERE address=? AND postcode=?
    List<Address1> findByAddressAndPostcode(String address, String postcode);

    // member1이 객체이기 때문에 _를 이용해서 id값
    // SELECT * FROM address1 WHERE member1.id=? ORDER BY no DESC
    List<Address1> findByMember1_idOrderByNoDesc(String id);

    // 전체 개수
    // SELECT COUNT(*) FROM address1 WHERE member1.id=?
    long countByMember1_id(String id);

    // SELECT * FROM address1 WHERE member1.id=? ORDER BY no DESC + 페이지네이션 기능 포함
    List<Address1> findByMember1_idOrderByNoDesc(String id, Pageable pageable);

    // -------------------------------------------------------------------------------------
    // projection

    // SELECT a.no, a.address, m.id, m.name FROM address1 a, member1 m ORDER BY no DESC
    // List<Address1Projection> findAllByOrderByNoDesc();

    // List<Address1> findAllByOrderByNoDesc();
    // 메소드명이 같은데 서로 반환 타입이 다르면 오류발생 => 제너릭 사용

    // 제너릭을 이용한 타입 설정
    // 제너릭 => 같은 메소드를 여러 타입으로 쓸 수 있음
    <T> List<T> findAllByOrderByNoDesc(Class<T> type);
}
