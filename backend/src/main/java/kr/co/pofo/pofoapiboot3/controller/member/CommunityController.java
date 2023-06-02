package kr.co.pofo.pofoapiboot3.controller.member;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.pofo.pofoapiboot3.entity.Community;
import kr.co.pofo.pofoapiboot3.service.CommunityService;
import kr.co.pofo.pofoapiboot3.util.FileUpload;

@RestController("memberCommunityController")
@RequestMapping("/members/community")
public class CommunityController {
    
    @Autowired
    private CommunityService service;
    @Autowired
    private FileUpload fileUpload;

    // 커뮤니티 등록
    @PostMapping("register")
    public boolean register(MultipartFile image, Community post, Boolean onlineType, HttpServletRequest request) throws IllegalStateException, IOException{
        String originalFilename = image.getOriginalFilename();
        String modifiedName = fileUpload.modifyImgName(originalFilename);

        fileUpload.upload(image, modifiedName, 2);
        post.setThumbnail(modifiedName);
        boolean result = service.register(post);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    
}
