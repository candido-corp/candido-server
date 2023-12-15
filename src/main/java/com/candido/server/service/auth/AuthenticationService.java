package com.candido.server.service.auth;

import com.biotekna.doctor.security.domain.account.Account;
import com.biotekna.doctor.security.dto.AuthenticationRequest;
import com.biotekna.doctor.security.dto.AuthenticationResponse;
import com.biotekna.doctor.security.dto.PasswordResetRequest;
import com.biotekna.doctor.security.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    void register(RegisterRequest request, String ipAddress, String appUrl);
    void verifyRegistrationToken(String registrationToken);
    AuthenticationResponse authenticate(AuthenticationRequest request, String ipAddress);
    AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
    void sendResetPassword(String email, String ipAddress, String appUrl);
    AuthenticationResponse resetPassword(String resetToken, PasswordResetRequest request, String ipAddress);
    Account getAccountAndVerifyToken(String token, int tokenScopeCategoryId);
}
