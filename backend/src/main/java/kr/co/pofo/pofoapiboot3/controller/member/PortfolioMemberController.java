package kr.co.pofo.pofoapiboot3.controller.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.entity.PortfolioContents;
import kr.co.pofo.pofoapiboot3.service.PortfolioService;
import kr.co.pofo.pofoapiboot3.service.SkillService;
import kr.co.pofo.pofoapiboot3.util.FileUpload;
@RestController
@RequestMapping("/members")
public class PortfolioMemberController {
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private FileUpload fileUpload;
    private int id;

    @PostMapping("/regpofo")
    public boolean regPofo(Portfolio pofo,String [] skills, MultipartFile image) throws Exception{
        //UUID로 파일명 변경
        String modifiedName = fileUpload.modifyImgName(image.getOriginalFilename());
        //파일 업로드 실행
        fileUpload.upload(image, modifiedName,0);
        //UUID로 변경된 썸네일 이름 portfolio객체에 setting
        pofo.setThumbnail(modifiedName);
        boolean result = portfolioService.regPofo(pofo);
        
        id = portfolioService.getByTitleAndMemberId(pofo.getTitle(), pofo.getMemberId());
        
        skillService.regPofoSkills(id,skills);
        return result;
    }

    @PostMapping("/regcontent")
    public boolean regContent(@RequestParam(required = false) MultipartFile contents, 
                   MultipartHttpServletRequest request, int orders, int counts) {
       int types = 0;
       System.out.println("counts" + counts);
       System.out.println("orders" + orders);
       PortfolioContents pofoContent = new PortfolioContents(); 
       pofoContent.setOrders(orders);
       pofoContent.setPortfolioId(id);
       String content = "";
       // null이면 문자열 데이터
       if(contents==null) {
           content = request.getParameter("contents");
           pofoContent.setContent(content);
           pofoContent.setType((Integer.toString(types)));
           boolean result = portfolioService.regContent(pofoContent);
       }
       //아니면 파일 데이터
       else {
          types =1;
          content = fileUpload.modifyImgName(contents.getOriginalFilename());
          fileUpload.upload(contents, content, 1);
          pofoContent.setContent(content);
          pofoContent.setType((Integer.toString(types)));
          boolean result = portfolioService.regContent(pofoContent);
       }
       if((orders+1) == counts)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       return true;
    }
}
