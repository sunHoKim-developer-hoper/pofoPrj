package kr.co.pofo.pofoapiboot3.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.co.pofo.pofoapiboot3.entity.Member;
import kr.co.pofo.pofoapiboot3.repository.MemberRepository;

@Service
public class DefaultMemberService implements MemberService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSenderImpl sender;

    private Map<String, Integer> map = new HashMap<>();

    public int signup(Member member) {
        member.setPwd(passwordEncoder.encode(member.getPwd()));
        return repository.insert(member);
    };

    public Member getById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean isValid(String email, String password) {
        Member member = getByEmail(email);
        if (member == null)
            return false;

        else if (!BCrypt.checkpw(password, member.getPwd()))
            return false;

        return true;
    }

    @Override
    public int modifyInfo(Member member) {
        if (member.getPwd() != null) {
            member.setPwd(passwordEncoder.encode(member.getPwd()));
        }
        return repository.update(member);
    }

    @Override
    public Member getByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public int checkEmail(String email) {
        int result = repository.countEmail(email);
        return result;
    }

    @Override
    public void sendEmail(String email) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        int randomNum = createRandomNum();
        map.put(email, randomNum);
        try {
            helper.setTo(email);
            helper.setSubject("Portfo 회원가입 인증 이메일");
            helper.setText("이메일 인증번호 : " + randomNum);
            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // 난수 생성 메서드
    public int createRandomNum() {
        Random ran = new Random();
        int randomNum = (ran.nextInt(500000) + 1) + (ran.nextInt(499999) + 1);
        return randomNum;
    }

    @Override
    public boolean checkAuthoNum(int authoNum, String email) {
        if (authoNum == (int) map.get(email)) {
            map.remove(email);
            return true;
        } else
            return false;
    }

    @Override
    public int checkNickname(String nickname) {
        return repository.countNickname(nickname);
    }

    @Override
    public int addUUID(String email, String uuid) {
        return repository.insertUUID(email, uuid);
    }

    @Override
    public String getImageById(int id) {
       return repository.findImageById(id);
    }
}
