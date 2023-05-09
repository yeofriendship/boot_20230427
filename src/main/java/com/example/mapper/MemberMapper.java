package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Member;

@Mapper
public interface MemberMapper {
    // 회원가입
    public int insertMemberOne(Member member);

    // 로그인
    public Member selectMemberOne(Member member);

    // 아이디를 전달해서 해당 아이디의 고객 정보 반환
    public Member selectMemberOne1(String userid);

    // 회원 정보 수정 (이름, 나이)
    public int updateMemberOne(Member member);

    // 비밀번호 변경
    public int updateMemberPw(Member member);

    // 회원 탈퇴
    public int deleteMemberOne(Member member);
}
