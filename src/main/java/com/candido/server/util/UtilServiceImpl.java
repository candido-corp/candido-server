package com.candido.server.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Service
public class UtilServiceImpl implements UtilService {

    @Value("${application.client.domain}")
    private String clientDomain;

    @Override
    public String getAppUrl(HttpServletRequest request) {
        return (request.isSecure() ? "https" : "http") +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

    @Override
    public String getClientIP(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    public int countDigits(long number) {
        number = Math.abs(number);
        return (int) (Math.log10(number) + 1);
    }

    @Override
    public String getTemplateContentFromLocalResources(String pathResource, String functionError) {
        String text = "";
        try {
            ClassPathResource resource = new ClassPathResource(pathResource);
            byte[] fileContent = StreamUtils.copyToByteArray(resource.getInputStream());
            text = new String(fileContent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("[{}] at {} -> {}", functionError, LocalDateTime.now(), e.getMessage(), e);
        }
        return text;
    }

    @Override
    public String buildVerificationLink(String token) {
        return UriComponentsBuilder
                .fromHttpUrl(clientDomain)
                .path("/auth/register/verify/")
                .queryParam("token", token)
                .toUriString();
    }

    @Override
    public String buildCodeVerificationLink(String token) {
        return UriComponentsBuilder
                .fromHttpUrl(clientDomain)
                .path("/auth/register/verify/code")
                .queryParam("token", token)
                .toUriString();
    }

}
