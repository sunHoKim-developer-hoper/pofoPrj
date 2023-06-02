package kr.co.pofo.pofoapiboot3.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.pofo.pofoapiboot3.entity.Member;
import kr.co.pofo.pofoapiboot3.service.MemberService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MemberService service;

    @PostMapping("/signup")
    public String signup(Member member) {
        service.signup(member);
        return "ok";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(String email, String password) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("result", false);
        if (service.isValid(email, password)) {
            Member member = service.getByEmail(email);
            dto.put("result", member);
        }
        return new ResponseEntity<Map<String, Object>>(dto, HttpStatus.OK);
    }
    
    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> loginByEmail(String email, String password) {
        Map<String, Object> dto = new HashMap<>();
            Member member = service.getByEmail(email);
        dto.put("result", member);
        return new ResponseEntity<Map<String, Object>>(dto, HttpStatus.OK);
    }




    @GetMapping("/nicknamecheck")
    public String nicknameCheck(String nickname) {
        int result = service.checkNickname(nickname);
        if (result == 1)
            return "no";
        else
            return "ok";
    }

    @GetMapping("/newuserinfo/{id}")
    public Map<String, Member> getNewUserInfo(@PathVariable("id") int id){
        Member member = service.getById(id);
        Map<String, Member> map = new HashMap<>();
        map.put("result", member);
        return map;
    }

    @GetMapping("/image/{id}")
    public String getImageById(@PathVariable int id){
        String image = service.getImageById(id);
        return image;
    }
}
