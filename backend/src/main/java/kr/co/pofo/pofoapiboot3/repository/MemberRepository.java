package kr.co.pofo.pofoapiboot3.repository;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pofo.pofoapiboot3.entity.Member;

@Mapper
public interface MemberRepository {
	// 로그인 시 로그인 아이디로 멤버 객체 찾기
	Member findByEmail(String email);

	// 회원 가입 insert
	int insert(Member member);

	int countEmail(String email);

	int countNickname(String nickname);

	Member findById(int id);

	int insertUUID(String email, String uuid);

	int update(Member member);

    String findImageById(int id);
}
