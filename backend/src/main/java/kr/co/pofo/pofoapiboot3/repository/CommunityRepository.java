package kr.co.pofo.pofoapiboot3.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pofo.pofoapiboot3.entity.Community;
import kr.co.pofo.pofoapiboot3.entity.CommunityView;

@Mapper
public interface CommunityRepository {

    // 커뮤니티 등록
    int insert(Community post);
    Long findIdByMemberId(Long memberId);

    // 커뮤니티 목록조회
    List<CommunityView> findViewAll();

    // 커뮤니티 상세조회
    CommunityView findById(Long id);
    int updateCloseFlag(int communityId);
}
