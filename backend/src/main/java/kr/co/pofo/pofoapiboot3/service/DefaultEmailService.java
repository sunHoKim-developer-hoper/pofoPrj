package kr.co.pofo.pofoapiboot3.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.pofo.pofoapiboot3.repository.EmailRepository;

@Service
public class DefaultEmailService implements EmailService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private MemberService service;

    @Autowired
    private EmailRepository repository;

    @Override
    public int emailCheck(String email) {
        int result = repository.countEmail(email);
        return result;
    }

    @Override
    public String getId(String email) {
        return repository.findId(email);
    }

    @Override
    public void sendLink(String email, HttpSession session) {
        System.out.println("실행????????" + email);
        String uuid = UUID.randomUUID().toString();
        service.addUUID(email, uuid);
        session.setAttribute("email", email);
        String subject = "요청 하신 비밀번호 재설정 링크 입니다.";
        String resetPasswordLink = String.format("http://localhost:5173/#/pwdreset?uuid=%s", uuid);
        String body = "<br/>해당링크로 접속하셔서 비밀번호를 변경하실수 있습니다. <br/>" + resetPasswordLink;

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("email : " + (String) session.getAttribute(email));
        sender.send(mimeMessage);

    }

    @Override
    public String uuidCheck(String email) {
        String uuid = repository.uuidCheck(email);
        return uuid;
    }

    @Override
    public int modifyPwd(String pwd, String uuid) {
        String enPwd = (passwordEncoder.encode(pwd));
        return repository.modifyPwd(enPwd, uuid);
    };
}
