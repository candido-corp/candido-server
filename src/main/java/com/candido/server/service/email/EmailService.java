package com.candido.server.service.email;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.user.User;

import java.util.Map;

public interface EmailService {
    void sendSimpleMessage(String from, String fromPersonal, String to, String subject, String text);
    String buildEmailContent(String template, Map<String, Object> variables);
    String buildRegistrationEmailContent(Account account, String linkToVerify);
    String buildCodeVerificationEmailContent(Account account, String temporaryCode, String linkToVerify);
    String buildRegistrationCompletedEmailContent(Account account, User user);
    String buildResetPasswordEmailContent(Account account, String linkToVerify);
    String buildResetPasswordCompletedEmailContent(Account account);
}
