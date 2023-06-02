package kr.co.pofo.pofoapiboot3.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pofo.pofoapiboot3.entity.Follow;
import kr.co.pofo.pofoapiboot3.entity.Member;

@Mapper
public interface FollowRepository {
    void insert(Follow follow);
    void delete(Follow follow);
    int countFollower(int id);
    int countFollowing(int id);
    
    int count(Follow follow);
    
    List<Member> findFollower(int id);
    List<Member> findFollowing(int id);
}
