package kr.co.pofo.pofoapiboot3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import kr.co.pofo.pofoapiboot3.entity.Member;
import kr.co.pofo.pofoapiboot3.service.EmailService;
import kr.co.pofo.pofoapiboot3.service.MemberService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private MemberService service;

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendemail")
    public String sendEmail(String email) {
        // 이메일 중복확인 용
        int result = service.checkEmail(email);
        if (result == 1)
            return "";
        else {
            service.sendEmail(email);
            return "OK";
        }
    }

    @GetMapping("/checkemail")
    public String checkEmail(String email) {
        // 이메일 중복확인 용
        int result = service.checkEmail(email);
        if (result == 1)
            return "no";
        else {
            return "ok";
        }
    }

    // 이메일 인증번호 확인
    @GetMapping("check")
    public String checkNum(@RequestParam(name = "certificationnum") int number, String email) {

        boolean result = service.checkAuthoNum(number, email);
        if (result)
            return "ok";
        else
            return "";
    }

    @PostMapping("/findpwd")
    @ResponseBody
    public String submitForm(Member member, HttpSession session) throws MessagingException {
        String email = member.getEmail();
        int emailResult = emailService.emailCheck(email);
        if (emailResult == 1) {
            emailService.sendLink(email, session);
            return "OK";
        } else {
            return "NO";
        }
    }

    @GetMapping("/uuidcheck")
    public String uuidCheck(@RequestParam String uuid) {
        String uuidCheck = emailService.uuidCheck(uuid);
        if (Integer.parseInt(uuidCheck) == 1) {
            return "ok";
        }
        return "no";
    }

    @PostMapping("/modifypwd")
    public String modifyPwd(String pwd, @RequestParam String uuid) {
        int result = emailService.modifyPwd(pwd, uuid);
        if (result == 1) {
            return "ok";
        } else {
            return "no";
        }
    }
}