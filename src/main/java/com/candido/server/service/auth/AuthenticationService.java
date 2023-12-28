package com.candido.server.service.auth;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    void register(RequestRegister request, String ipAddress, String appUrl);
    void verifyRegistrationToken(String registrationToken);
    void verifyRegistrationByEmail(String email);
    ResponseAuthentication authenticate(RequestAuthentication request, String ipAddress);
    ResponseAuthentication refreshToken(HttpServletRequest request, HttpServletResponse response);
    void sendResetPassword(String email, String ipAddress, String appUrl);
    ResponseAuthentication resetPassword(String resetToken, RequestPasswordReset request, String ipAddress);
    Account getAccountAndVerifyToken(String token, int tokenScopeCategoryId);
}
