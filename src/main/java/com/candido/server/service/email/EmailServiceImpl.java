package com.candido.server.service.email;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${application.email.can-send}")
    private boolean smtpCanSend;

    @Autowired
    private SpringTemplateEngine templateEngine;

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

    public String buildEmailContent(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process("email/" + templateName, context);
    }

    @Override
    public String buildRegistrationEmailContent(Account account, String linkToVerify) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", account.getUsername());
        variables.put("registrationUrl", linkToVerify);
        return buildEmailContent("registration", variables);
    }

    @Override
    public String buildCodeVerificationEmailContent(Account account, String temporaryCode, String linkToVerify) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", account.getUsername());
        variables.put("registrationCode", temporaryCode);
        variables.put("registrationUrl", linkToVerify);
        return buildEmailContent("registration_by_code", variables);
    }

    @Override
    public String buildRegistrationCompletedEmailContent(Account account, User user) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", account.getUsername());
        variables.put("first_name", user.getFirstName());
        variables.put("last_name", user.getLastName());
        return buildEmailContent("registration_completed", variables);
    }

    @Override
    public String buildResetPasswordEmailContent(Account account, String linkToVerify) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", account.getUsername());
        variables.put("resetUrl", linkToVerify);
        return buildEmailContent("reset_password", variables);
    }

    @Override
    public String buildResetPasswordCompletedEmailContent(Account account) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", account.getUsername());
        return buildEmailContent("reset_password_completed", variables);
    }

}
