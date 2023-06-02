package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import kr.co.pofo.pofoapiboot3.entity.Community;
import kr.co.pofo.pofoapiboot3.entity.CommunityView;
import kr.co.pofo.pofoapiboot3.repository.CommunityRepository;
import kr.co.pofo.pofoapiboot3.repository.CommunityTeamRepository;

@Service
// @EnableTransactionManagement
public class DefaultCommunityService implements CommunityService {

    @Autowired
    private CommunityRepository repository;

    @Autowired
    private CommunityTeamRepository teamRepository;
    
    // 커뮤니티 등록
    // @Transactional
    @Override
    public boolean register(Community post) {
        int result = repository.insert(post);

        if(result == 1){ // insert 성공
            Long id = repository.findIdByMemberId(post.getMemberId());
            teamRepository.insert(id, post.getMemberId()); // 팀 등록
            return true;
        } 
        else {           // insert 실패
            return false;
        }           
    }

    // 커뮤니티 목록조회
    @Override
    public List<CommunityView> getViewList() {
        return repository.findViewAll();
    }

    // 커뮤니티 상세조회
    @Override
    public CommunityView getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Community> getByUserId(int id) {
        return teamRepository.findByUserId(id);
    }

    @Override
    public void close(int communityId) {
        repository.updateCloseFlag(communityId);
    }
}
