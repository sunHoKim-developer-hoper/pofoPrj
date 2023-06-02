package kr.co.pofo.pofoapiboot3.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pofo.pofoapiboot3.entity.Community;
import kr.co.pofo.pofoapiboot3.entity.CommunityTeam;

@Mapper
public interface CommunityTeamRepository {

    // 팀 등록
    void insert(Long communityId, Long memberId);
    CommunityTeam findInfo(CommunityTeam team);
    void create(int memberId, int communityId);
    void reject(int memberId, int communityId);
    List<Community> findByUserId(int id);
}
