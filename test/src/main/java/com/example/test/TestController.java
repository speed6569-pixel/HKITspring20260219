package com.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TestController {
	
	@Autowired
	private EmailUtil emailUtil;


	@GetMapping("/email")
	
	public String hello() {
		return "email-form";
	}
	
	// 이메일 전송 처리
	@PostMapping("/send-email")
	public String sendEmail(@RequestParam(value="to") String to,
							@RequestParam(value="subject") String subject,
							@RequestParam(value="content") String content,
							RedirectAttributes redirectAttributes) {
		emailUtil.sendTextEmail(to, subject, content);
		redirectAttributes.addFlashAttribute("success", "이메일 전송완료");
		return "redirect:/email";
	}
	
	@PostMapping("/send-welcome-email")
	public String sendEmail(@RequestParam(value="userEmail") String userEmail,
							@RequestParam(value="userName") String userName,
							RedirectAttributes redirectAttributes) {
		emailUtil.sendWelcomemail(userEmail, userName);
		redirectAttributes.addFlashAttribute("success", "이메일 전송완료");
		return "redirect:/email";
	}
}

// SMTP(Simple Mail Transfer Protocol) : 인터넷을 통해 이메일을 보낼때 사용하는 표준 프로토콜
// -> 일종의 디지털 우체부
// -> 기본적으로는 텍스트 기반.
// POP3/ IMA{
// SMTP는 오직 보내는것에만 특화되어있음. 받는 것은 POP3/ IMAP을 통해 처리.
// POP3 : 서버의 메일을 내 기기로 가져올때.(가져온뒤에는 서버에서 메일은 삭제)
// IMAP : 서버와 내 기기를 동기화 할때(여러 기기에서 동일한 상태 확인 가능)


