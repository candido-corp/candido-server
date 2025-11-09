package com.candido.server.service.base.email;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;

import java.util.Map;

public interface EmailService {
    void sendSimpleMessage(String from, String fromPersonal, String to, String subject, String text);
    String buildEmailContent(String template, Map<String, Object> variables);
    String buildRegistrationEmailContent(Account account, User user, String linkToVerify);
    String buildCodeVerificationEmailContent(Account account, User user, String temporaryCode, String linkToVerify);
    String buildRegistrationCompletedEmailContent(Account account, User user, String ipAddress);
    String buildResetPasswordEmailContent(Account account, User user, String linkToVerify);
    String buildResetPasswordCompletedEmailContent(Account account, User user);
}
