package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import kr.co.pofo.pofoapiboot3.entity.Community;
import kr.co.pofo.pofoapiboot3.entity.CommunityView;

public interface CommunityService {

    // 커뮤니티 등록
    boolean register(Community post);

    // 커뮤니티 목록조회
    List<CommunityView> getViewList();

    // 커뮤니티 상세조회
    CommunityView getById(Long id);

    List<Community> getByUserId(int id);

    void close(int communityId);
}
