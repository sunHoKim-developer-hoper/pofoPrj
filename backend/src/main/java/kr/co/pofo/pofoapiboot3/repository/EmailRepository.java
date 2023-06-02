package kr.co.pofo.pofoapiboot3.repository;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pofo.pofoapiboot3.entity.Member;

@Mapper
public interface EmailRepository {

    int countEmail(String email);

    String findId(String email);

    int findEmail(String email);

    String uuidCheck(String uuid);

    int modifyPwd(String pwd, String uuid);
}
