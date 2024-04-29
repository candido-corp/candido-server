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

        String linkToVerify = utilService.buildEmailVerificationLink(event.getRegistrationToken());
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

        String content = emailService.buildRegistrationCompletedEmailContent(
                event.getAccount(), event.getUser()
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
        log.info("[Candido::ResetPassword] Account -> {}", event.getAccount());

        String linkToVerify = utilService.buildResetPasswordLink(event.getResetToken());
        String content = emailService.buildResetPasswordEmailContent(
                event.getAccount(), linkToVerify
        );

        emailService.sendSimpleMessage(
                noReply,
                applicationName,
                event.getAccount().getEmail(),
                ConstEmailSubject.RESET_PASSWORD_SUBJECT,
                content
        );
    }

    @Async
    @Override
    @EventListener
    public void handleOnResetPasswordCompletedEvent(OnResetAccountCompletedEvent event) {
        log.info("[Candido::ResetPasswordCompleted] Account -> {}", event.getAccount());

        String content = emailService.buildResetPasswordCompletedEmailContent(
                event.getAccount()
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
