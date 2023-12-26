package com.candido.server.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${application.email.can-send}")
    private boolean smtpCanSend;

    @Override
    public void sendSimpleMessage(String from, String fromPersonal, String to, String subject, String text) {
        if(smtpCanSend) {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = null;
            try {
                helper = new MimeMessageHelper(message, true);
                message.setContent(text, "text/html; charset=utf-8");
                helper.setFrom(new InternetAddress(from, fromPersonal));
                helper.setTo(to);
                helper.setSubject(subject);
                emailSender.send(message);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
