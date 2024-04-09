package com.candido.server.util;

import jakarta.servlet.http.HttpServletRequest;

public interface UtilService {
    String getAppUrl(HttpServletRequest request);
    String getClientIP(HttpServletRequest request);
    int countDigits(long number);
    String getTemplateContentFromLocalResources(String pathResource, String functionError);

    String buildVerificationLink(String token);
    String buildCodeVerificationLink(String token);
}
