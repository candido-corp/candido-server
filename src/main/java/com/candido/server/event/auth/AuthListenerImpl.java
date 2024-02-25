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

        String linkToVerify = clientDomain + "/auth/register/verify/" + event.getRegistrationToken();

        String content;
        String subject = "Confirm your identity";

        if (event.isRegistrationByCodeVerification()) {
            subject = "Here's your verification code " + event.getTemporaryCode();
            content = utilService.getTemplateContentFromLocalResources(
                            "/static/email/registration_by_code.html",
                            "Candido::Error::handleOnRegistrationEvent"
                    )
                    .replace("{{registration.code}}", String.valueOf(event.getTemporaryCode()));
        } else {
            content = utilService.getTemplateContentFromLocalResources(
                    "/static/email/registration.html",
                    "Candido::Error::handleOnRegistrationEvent"
            );
        }

        content = content
                .replace("{{registration.username}}", event.getAccount().getUsername())
                .replace("{{registration.url}}", linkToVerify);

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                subject,
                content
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnRegistrationCompletedEvent(OnRegistrationCompletedEvent event) {
        log.info("[Candido::RegistrationCompleted] Account -> {}", event.getAccount());

        String content = utilService.getTemplateContentFromLocalResources(
                        "/static/email/registration_completed.html",
                        "Candido::Error::handleOnRegistrationCompletedEvent"
                )
                .replace("{{registration.username}}", event.getAccount().getUsername())
                .replace("{{registration.first_name}}", event.getUser().getFirstName())
                .replace("{{registration.last_name}}", event.getUser().getLastName());

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                "Registration confirmed",
                content
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnResetAccountEvent(OnResetAccountEvent event) {
        log.info("[Candido::ResetAccount] Account -> {}", event.getAccount());

        String linkToVerify = clientDomain + "/auth/reset/" + event.getResetToken();

        String content = utilService.getTemplateContentFromLocalResources(
                        "/static/email/reset_password.html",
                        "Candido::Error::handleOnResetAccountEvent"
                )
                .replace("{{reset.name}}", event.getAccount().getUsername())
                .replace("{{reset.url}}", linkToVerify);

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                "Password reset",
                content
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnResetAccountCompletedEvent(OnResetAccountCompletedEvent event) {
        log.info("[Candido::ResetAccountCompleted] Account -> {}", event.getAccount());

        String content = utilService.getTemplateContentFromLocalResources(
                        "/static/email/reset_password_completed.html",
                        "Candido::Error::handleOnResetAccountCompletedEvent"
                )
                .replace("{{reset.username}}", event.getAccount().getUsername());

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                "Password reset completed",
                content
        );
    }

}
