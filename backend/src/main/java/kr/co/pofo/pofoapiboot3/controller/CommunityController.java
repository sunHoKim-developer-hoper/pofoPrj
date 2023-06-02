package kr.co.pofo.pofoapiboot3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.pofo.pofoapiboot3.entity.CommunityTeam;
import kr.co.pofo.pofoapiboot3.entity.CommunityView;
import kr.co.pofo.pofoapiboot3.entity.Notification;
import kr.co.pofo.pofoapiboot3.service.CommunityService;
import kr.co.pofo.pofoapiboot3.service.CommunityTeamService;
import kr.co.pofo.pofoapiboot3.service.NotificationService;


@RestController
@RequestMapping("/community")
public class CommunityController {
    
    @Autowired
    private CommunityService service;

    @Autowired
    private CommunityTeamService communityTeamService;

    @Autowired
    private NotificationService notificationService;

    // 커뮤니티 목록조회
    @GetMapping("list")
    public ResponseEntity<Map<String, Object>> list(){
        
        List<CommunityView> list = service.getViewList();
        
        Map<String, Object> dto = new HashMap<>();
        dto.put("list", list);

        return new ResponseEntity<Map<String, Object>>(dto, HttpStatus.OK);
    }

    // 커뮤니티 상세조회
    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> detail(@PathVariable("id") Long id){

        CommunityView community = service.getById(id);
        
        Map<String, Object> dto = new HashMap<>();
        dto.put("community", community);

        return new ResponseEntity<Map<String,Object>>(dto, HttpStatus.OK);
    }

    // 팀 신청 확인
    @PostMapping("getApplicationStatus")
    public boolean getApplicationStatus(Notification notification) {
        System.out.println(notification);
        boolean result = notificationService.isApplied(notification);
        System.out.println(result);

        return result;
    }

    // 팀 신청
    /*
     * 팀원은 신청 
     * -> notification 테이블에 (type_id, from_member_id, to_member_id, community_id) 삽입
     * 팀장은 수락
     */
    @PostMapping("apply")
    public String apply(Notification notification) {
        notificationService.create(notification);
        return null;
    }

    // 팀 신청 취소
    @DeleteMapping("cancle")
    public String cancle(Notification notification) {
        notificationService.delete(notification);
        return null;
    }
    
    @GetMapping("getcommunityteaminfo")
    public CommunityTeam getCommunityTeamInfo(CommunityTeam team){
        if(communityTeamService.getInfo(team) == null)
            return new CommunityTeam();
        return communityTeamService.getInfo(team);
    }

    @PostMapping("accept")
    public String accept (Integer memberId, Integer communityId, int id){
        communityTeamService.add(memberId, communityId);
        notificationService.updateAcceptFlag(id);
        return "ok";
    }

    @PostMapping("reject")
    public String reject (Integer memberId, Integer communityId, int id){
        communityTeamService.addReject(memberId, communityId);
        notificationService.updateReject(id);
        return "ok";
    }

    @GetMapping("close")
    public String closeCommu(int communityId){
        service.close(communityId);
        return "ok";
    }
}
