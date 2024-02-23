package com.candido.server.service.auth;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.token.Token;
import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface AuthenticationService {
    ResponseRegistration register(RequestRegister request, String ipAddress, String appUrl, boolean isEmailVerification);

    void verifyRegistrationByToken(String registrationToken);
    void verifyRegistrationBySessionIdAndTemporaryCode(String sessionId, String temporaryCode);
    Account getAccountAndVerifyToken(String token, int tokenScopeCategoryId);
    void resendCodeRegistrationBySessionId(String sessionId);

    ResponseAuthentication authenticate(RequestAuthentication request, String ipAddress);
    ResponseAuthentication refreshToken(HttpServletRequest request, HttpServletResponse response);
    void sendResetPassword(String email, String ipAddress, String appUrl);
    ResponseAuthentication resetPassword(String resetToken, RequestPasswordReset request, String ipAddress);


    // TODO: Elimina i servizi qui sotto
    void verifyRegistrationByEmail(String email);
    List<Token> getListOfTokenByEmail(String email);
}
