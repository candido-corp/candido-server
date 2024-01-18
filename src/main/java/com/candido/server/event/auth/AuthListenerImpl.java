package com.candido.server.event.auth;

import com.candido.server.service.email.EmailService;
import com.candido.server.util.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Component
public class AuthListenerImpl implements AuthListenerService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilService utilService;

    @Value("${application.name}")
    private String applicationName;

    @Value("${application.email.no-reply}")
    private String noReply;

    @Value("${application.client.domain}")
    private String clientDomain;

    @Async
    @Override
    @EventListener
    public void handleOnRegistrationEvent(OnRegistrationEvent event) {
        log.info("[Candido::Registration] Account -> {}", event.getAccount());

        String text = "";
        try {
            ClassPathResource resource = new ClassPathResource("/static/email/registration.html");
            byte[] fileContent = StreamUtils.copyToByteArray(resource.getInputStream());
            text = new String(fileContent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("[Candido::Error::handleOnRegistrationEvent] at {} -> {}", LocalDateTime.now(), e.getMessage());
        }

        String linkToVerify = clientDomain + "/auth/register/verify/" + event.getRegistrationToken();
        text = text
                .replace("{{registration.username}}", event.getAccount().getUsername())
                .replace("{{registration.url}}", linkToVerify);

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                "Confirm your registration",
                text
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnRegistrationCompletedEvent(OnRegistrationCompletedEvent event) {
        log.info("[Candido::RegistrationCompleted] Account -> {}", event.getAccount());

        String text = "";
        try {
            ClassPathResource resource = new ClassPathResource("/static/email/registration_completed.html");
            byte[] fileContent = StreamUtils.copyToByteArray(resource.getInputStream());
            text = new String(fileContent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("[Candido::Error::handleOnRegistrationCompletedEvent] at {} -> {}", LocalDateTime.now(), e.getMessage());
        }

        text = text
                .replace("{{registration.username}}", event.getAccount().getUsername());

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                "Registration confirmed",
                text
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnResetAccountEvent(OnResetAccountEvent event) {
        log.info("[Candido::ResetAccount] Account -> {}", event.getAccount());

        String text = "";
        try {
            ClassPathResource resource = new ClassPathResource("/static/email/reset_password.html");
            byte[] fileContent = StreamUtils.copyToByteArray(resource.getInputStream());
            text = new String(fileContent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("[Candido::Error::handleOnResetAccountEvent] at {} -> {}", LocalDateTime.now(), e.getMessage());
        }

        String linkToVerify = clientDomain + "/auth/reset/" + event.getResetToken();
        text = text
                .replace("{{reset.name}}", event.getAccount().getUsername())
                .replace("{{reset.url}}", linkToVerify);

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                "Password reset",
                text
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnResetAccountCompletedEvent(OnResetAccountCompletedEvent event) {
        log.info("[Candido::ResetAccountCompleted] Account -> {}", event.getAccount());

        String text = "";
        try {
            ClassPathResource resource = new ClassPathResource("/static/email/reset_password_completed.html");
            byte[] fileContent = StreamUtils.copyToByteArray(resource.getInputStream());
            text = new String(fileContent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("[Candido::Error::handleOnResetAccountCompletedEvent] at {} -> {}", LocalDateTime.now(), e.getMessage());
        }

        text = text
                .replace("{{reset.username}}", event.getAccount().getUsername());

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                "Password reset completed",
                text
        );
    }

}
