package com.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component // 스프링에서 단순한 유틸성 클래스를 만들때 자주 사용하는 어노테이션
			// 사용의 목적은 코드 관리를 위해. 즉 유지보수성을 향상시키기 위해 사용.
			// 즉 뭘로 써도 상관은 없지만 의미적 구분(시멘틱적구분)
public class EmailUtil {

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	// 이메일전송과 관련된 인터페이스.
	// 단순 이메일발송, 첨부파일 포함한 이메일 발송 서비스도 제공.
	private JavaMailSender mailSender;

	public void sendTextEmail(String to, String subject, String content) {
		
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		mailSender.send(message);

	}

	public void sendWelcomeEmail(String userEmail, String nickname) {
		String subject = " " + nickname + "님, 환영합니다!";
		String htmlContent = """
				<div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
				    <h2 style="color: #4CAF50;">환영합니다! </h2>
				    <p>안녕하세요, <strong>%s</strong>님!</p>
				    <p>회원가입을 축하드립니다. 저희 서비스와 함께 즐거운 시간을 보내세요!</p>
				    <div style="background-color: #f5f5f5; padding: 15px; margin: 20px 0;">
				        <p><strong>다음 단계:</strong></p>
				        <ul>
				            <li>프로필을 완성해보세요</li>
				            <li>첫 번째 기능을 사용해보세요</li>
				            <li>문의사항이 있으면 언제든 연락주세요</li>
				        </ul>
				    </div>
				    <p>감사합니다! </p>
				</div>
				""".formatted(nickname);

		sendHtmlEmail(userEmail, subject, htmlContent);
	}

	private void sendHtmlEmail(String userEmail, String subject, String htmlContent) {
		// TODO Auto-generated method stub

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmail);
			helper.setTo(userEmail);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
		
			
			throw new RuntimeException("이메일 전송 실패", e);
		}

	}

}
