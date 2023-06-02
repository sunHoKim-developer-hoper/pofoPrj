package kr.co.pofo.pofoapiboot3.service;

import kr.co.pofo.pofoapiboot3.entity.CommunityTeam;

public interface CommunityTeamService {

    CommunityTeam getInfo(CommunityTeam team);

    void add(int memberId, int communityId);

    void addReject(int memberId, int communityId);

}
