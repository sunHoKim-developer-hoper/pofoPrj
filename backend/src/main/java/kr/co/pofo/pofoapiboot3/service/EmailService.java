package kr.co.pofo.pofoapiboot3.service;

import jakarta.servlet.http.HttpSession;

public interface EmailService {

	int emailCheck(String email);

	String getId(String email);

	void sendLink(String email, HttpSession session);

	String uuidCheck(String uuid);

	int modifyPwd(String pwd, String uuid);

}
