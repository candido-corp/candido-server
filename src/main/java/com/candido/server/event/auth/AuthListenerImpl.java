package com.candido.server.event.auth;

import com.candido.server.domain.v1.email.ConstEmailSubject;
import com.candido.server.service.email.EmailService;
import com.candido.server.util.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
    public void handleOnEmailRegistrationEvent(OnEmailRegistrationEvent event) {
        log.info("[Candido::EmailRegistration] Account -> {}", event.getAccount());

        String linkToVerify = utilService.buildVerificationLink(event.getRegistrationToken());
        String content = emailService.buildRegistrationEmailContent(event.getAccount(), linkToVerify);

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                ConstEmailSubject.EMAIL_VERIFICATION_SUBJECT,
                content
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnCodeRegistrationEvent(OnCodeRegistrationEvent event) {
        log.info("[Candido::CodeRegistration] Account -> {}", event.getAccount());

        String linkToVerify = utilService.buildCodeVerificationLink(event.getRegistrationToken());
        String content = emailService.buildCodeVerificationEmailContent(event.getAccount(), event.getTemporaryCode(), linkToVerify);

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                ConstEmailSubject.CODE_VERIFICATION_SUBJECT + " " + event.getTemporaryCode(),
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
