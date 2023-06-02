package kr.co.pofo.pofoapiboot3.service;

import kr.co.pofo.pofoapiboot3.entity.Member;

public interface MemberService {
    // 회원가입
    int signup(Member member);

    Member getById(int id);

    // 로그인 아이디, 비밀번호 확인
    boolean isValid(String email, String pwd);

    // 이메일로 회원정보 가져오기
    Member getByEmail(String email);

    // 이메일 중복 체그
    int checkEmail(String email);

    // 인증번호 보내는 기능
    void sendEmail(String email);

    // 인증번호 일치 여부 확인
    boolean checkAuthoNum(int authoNum, String email);

    int checkNickname(String nickname);

    int addUUID(String email, String uid);

    int modifyInfo(Member member);

    String getImageById(int id);
}
