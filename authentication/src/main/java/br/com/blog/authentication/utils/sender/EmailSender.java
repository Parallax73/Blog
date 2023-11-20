package br.com.blog.authentication.utils.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Configuration
@Slf4j
public class EmailSender {


    private final JavaMailSender mailSender;

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("parallax717@gmail.com");
        message.setTo(toEmail);
        message.setText("Thanks for creating an account");
        message.setSubject("Account created!!!");

        mailSender.send(message);
        log.info("Email sended to {}",toEmail);
    }
}
