package com.candido.server.service.base.email;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.email.ExceptionEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    @Value("${application.email.can-send}")
    private boolean smtpCanSend;

    private static final String PLACEHOLDER_USERNAME = "USERNAME";
    private static final String PLACEHOLDER_URL = "URL";
    private static final String PLACEHOLDER_FIRST_NAME = "FIRST_NAME";

    @Autowired
    EmailServiceImpl(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendSimpleMessage(String from, String fromPersonal, String to, String subject, String text) {
        log.info("[smtp_can_send::{}] Sending email to {} to {}", smtpCanSend, to, from);
        if(smtpCanSend) {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper;
            try {
                helper = new MimeMessageHelper(message, true);
                message.setContent(text, "text/html; charset=utf-8");
                helper.setFrom(new InternetAddress(from, fromPersonal));
                helper.setTo(to);
                helper.setSubject(subject);
                emailSender.send(message);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new ExceptionEmail(EnumExceptionName.ERROR_BUSINESS_EMAIL_NOT_SENT.name());
            }
        }
    }

    public String buildEmailContent(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process("email/" + templateName, context);
    }

    @Override
    public String buildRegistrationEmailContent(Account account, User user, String linkToVerify) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(PLACEHOLDER_FIRST_NAME, user.getFirstName());
        variables.put(PLACEHOLDER_URL, linkToVerify);
        return buildEmailContent("registration", variables);
    }

    @Override
    public String buildCodeVerificationEmailContent(Account account, User user, String temporaryCode, String linkToVerify) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(PLACEHOLDER_FIRST_NAME, user.getFirstName());
        variables.put(PLACEHOLDER_URL, linkToVerify);
        variables.put("CODE_FIRST_SPLIT", temporaryCode.substring(0, 3));
        variables.put("CODE_SECOND_SPLIT", temporaryCode.substring(3));
        return buildEmailContent("registration_by_code", variables);
    }

    @Override
    public String buildRegistrationCompletedEmailContent(Account account, User user, String ipAddress) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(PLACEHOLDER_USERNAME, account.getUsername());
        variables.put(PLACEHOLDER_FIRST_NAME, user.getFirstName());
        variables.put("LAST_NAME", user.getLastName());
        variables.put("IP", ipAddress);
        return buildEmailContent("registration_completed", variables);
    }

    @Override
    public String buildResetPasswordEmailContent(Account account, User user, String linkToVerify) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(PLACEHOLDER_FIRST_NAME, user.getFirstName());
        variables.put(PLACEHOLDER_URL, linkToVerify);
        return buildEmailContent("reset_password", variables);
    }

    @Override
    public String buildResetPasswordCompletedEmailContent(Account account, User user) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(PLACEHOLDER_FIRST_NAME, user.getFirstName());
        return buildEmailContent("reset_password_completed", variables);
    }

}
