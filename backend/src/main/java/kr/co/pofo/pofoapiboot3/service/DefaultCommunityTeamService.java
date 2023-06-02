package kr.co.pofo.pofoapiboot3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pofo.pofoapiboot3.entity.CommunityTeam;
import kr.co.pofo.pofoapiboot3.entity.Notification;
import kr.co.pofo.pofoapiboot3.repository.CommunityTeamRepository;

@Service
public class DefaultCommunityTeamService implements CommunityTeamService{

    @Autowired
    private CommunityTeamRepository communityTeamRepository;

    @Override
    public CommunityTeam getInfo(CommunityTeam team) {
        return communityTeamRepository.findInfo(team);
    }
    @Override
    public void add(int memberId, int communityId) {
        communityTeamRepository.create(memberId, communityId);
    }
    @Override
    public void addReject(int memberId, int communityId) {
        communityTeamRepository.reject(memberId, communityId);
    }
}
