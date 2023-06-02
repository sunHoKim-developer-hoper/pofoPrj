package kr.co.pofo.pofoapiboot3.controller.member;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.pofo.pofoapiboot3.entity.Activities;
import kr.co.pofo.pofoapiboot3.entity.Community;
import kr.co.pofo.pofoapiboot3.entity.Member;
import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.service.ActivitiesService;
import kr.co.pofo.pofoapiboot3.service.CollectionsService;
import kr.co.pofo.pofoapiboot3.service.CommunityService;
import kr.co.pofo.pofoapiboot3.service.MemberService;
import kr.co.pofo.pofoapiboot3.service.PortfolioLikeService;
import kr.co.pofo.pofoapiboot3.service.PortfolioService;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService service;
    @Autowired
    private PortfolioService pofoService;
    @Autowired
    private PortfolioLikeService portfolioLikeService;
    @Autowired
    private CollectionsService collectionsService;
    @Autowired
    private ActivitiesService activitiesService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CommunityService communityService;

    @GetMapping("/myprofile/{id}")
    public ResponseEntity<Map<String, Object>> myProfile(@PathVariable("id") int id) {
        Map<String, Object> map = new HashMap<>();
        Member member = service.getById(id);
        map.put("member", member);

        List<Portfolio> works = pofoService.getByUserId(id);
        map.put("works", works);

        List<Portfolio> likes = portfolioLikeService.getByUserId(id);
        map.put("likes", likes);

        List<Portfolio> collections = collectionsService.getByUserId(id);
        map.put("collections", collections);

        List<Community> communities = communityService.getByUserId(id);
        map.put("communities", communities);
        
        Activities activities = activitiesService.settingActivities(id);
        map.put("activities", activities);

        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @PutMapping("modify")
    public String modifyInfo(@RequestParam(required = false) MultipartFile profile, Member member) {
        String modifiedName = "";
        if (profile !=null && !profile.isEmpty()) {
            modifiedName= modifyImgName(profile.getOriginalFilename(), member.getId());
            upload(profile, modifiedName);
            member.setImage(modifiedName);
        }
        service.modifyInfo(member);

        try {
            if(profile !=null)
                Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    public void upload(MultipartFile img, String modifiedName) {
        String urlPath = "/profileImage" + File.separator + modifiedName;
        String realPath = request.getServletContext().getRealPath(urlPath);
        try {
            img.transferTo(new File(realPath));
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    public String modifyImgName(String originalName, Integer id) {
        int index = originalName.lastIndexOf(".");
        String extension = originalName.substring(index);
        String modifiedName = "profile" + id + extension;
        return modifiedName;
    }
}