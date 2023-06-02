package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pofo.pofoapiboot3.entity.Follow;
import kr.co.pofo.pofoapiboot3.entity.Member;
import kr.co.pofo.pofoapiboot3.repository.FollowRepository;

@Service
public class DefaultFollowService {
    @Autowired
    private FollowRepository followRepository;

    public void add(Follow follow) {
        followRepository.insert(follow);
    }
    public void delete(Follow follow) {
        followRepository.delete(follow);
    }

    public int checkFollowed(Follow follow) {
        int result = followRepository.count(follow);
        return result;
    }

    public List<Member> getList(int id, int type){
        List<Member> list = null;
        if(type == 0)
            list = followRepository.findFollowing(id);
        else
            list = followRepository.findFollower(id);
        return list;
    }

}
