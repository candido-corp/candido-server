package com.candido.server.event.auth;

import com.candido.server.domain.v1.email.ConstEmailSubject;
import com.candido.server.service.base.email.EmailService;
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

    private final EmailService emailService;
    private final UtilService utilService;

    @Value("${application.name}")
    private String applicationName;

    @Value("${application.email.no-reply}")
    private String noReply;

    @Value("${application.client.domain}")
    private String clientDomain;

    @Autowired
    AuthListenerImpl(EmailService emailService, UtilService utilService) {
        this.emailService = emailService;
        this.utilService = utilService;
    }

    @Async
    @Override
    @EventListener
    public void handleOnEmailRegistrationEvent(OnEmailRegistrationEvent event) {
        log.info("[Event::EmailRegistration] Account -> {}", event.getAccount());

        String email = event.getAccount().getEmail();
        String linkToVerify = utilService.buildEmailVerificationLink(event.getRegistrationToken(), email);
        String content = emailService.buildRegistrationEmailContent(event.getAccount(), event.getUser(), linkToVerify);

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                email,
                ConstEmailSubject.EMAIL_VERIFICATION_SUBJECT,
                content
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnCodeRegistrationEvent(OnCodeRegistrationEvent event) {
        log.info("[Event::CodeRegistration] Account -> {}", event.getAccount());

        String email = event.getAccount().getEmail();
        String linkToVerify = utilService.buildCodeVerificationLink(event.getRegistrationToken(), email);
        String content = emailService.buildCodeVerificationEmailContent(event.getAccount(), event.getUser(), event.getTemporaryCode(), linkToVerify);

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                email,
                ConstEmailSubject.CODE_VERIFICATION_SUBJECT + " " + event.getTemporaryCode(),
                content
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnRegistrationCompletedEvent(OnRegistrationCompletedEvent event) {
        log.info("[Event::RegistrationCompleted] Account -> {}", event.getAccount());

        String content = emailService.buildRegistrationCompletedEmailContent(
                event.getAccount(), event.getUser(), event.getIpAddress()
        );

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                ConstEmailSubject.REGISTRATION_COMPLETED_SUBJECT,
                content
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnResetPasswordEvent(OnResetAccountEvent event) {
        log.info("[Event::ResetPassword] Account -> {}", event.getAccount());

        String email = event.getAccount().getEmail();
        String linkToVerify = utilService.buildResetPasswordLink(event.getResetToken(), email);
        String content = emailService.buildResetPasswordEmailContent(
                event.getAccount(), event.getUser(), linkToVerify
        );

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                email,
                ConstEmailSubject.RESET_PASSWORD_SUBJECT,
                content
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnResetPasswordCompletedEvent(OnResetAccountCompletedEvent event) {
        log.info("[Event::ResetPasswordCompleted] Account -> {}", event.getAccount());

        String content = emailService.buildResetPasswordCompletedEmailContent(
                event.getAccount(), event.getUser()
        );

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                ConstEmailSubject.RESET_PASSWORD_COMPLETED_SUBJECT,
                content
        );
    }

}
