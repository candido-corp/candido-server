package com.candido.server.util;

import jakarta.servlet.http.HttpServletRequest;

public interface UtilService {
    String getAppUrl(HttpServletRequest request);
    String getClientIP(HttpServletRequest request);
    int countDigits(long number);
    String getTemplateContentFromLocalResources(String pathResource, String functionError);

    String buildEmailVerificationLink(String token, String email);
    String buildCodeVerificationLink(String token, String email);
    String buildResetPasswordLink(String token, String email);
}
