package com.candido.server.service.base.auth;

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
    ResponseAuthentication registerByEmail(RequestRegister request, String ipAddress, String appUrl);
    ResponseRegistration registerByCode(RequestRegister request, String ipAddress, String appUrl);

    ResponseAuthentication createAuthentication(Account account, String ipAddress, Token token);
    ResponseAuthentication authenticate(RequestAuthentication request, String ipAddress);
    ResponseAuthentication refreshToken(HttpServletRequest request, HttpServletResponse response);

    String getTokenFromAuthorizationHeaderRequest(HttpServletRequest request);

    // From email (UUID is correct)
    ResponseAuthentication verifyEmailRegistration(String uuidAccessToken, String email, String ipAddress);
    ResponseAuthentication verifyCodeRegistration(String uuidAccessToken, String temporaryCode, String email, String ipAddress);
    void checkValidityOfUUIDAccessTokenForResetPassword(String uuidAccessToken);
    ResponseAuthentication resetPassword(String uuidAccessToken, String email, RequestPasswordReset request, String ipAddress);

    // From web
    void resendEmailRegistration(String email, String appUrl, String ipAddress);
    void resendCodeRegistrationByUUIDAccessToken(String uuidAccessToken, String appUrl);
    void sendResetPassword(String email, String ipAddress, String appUrl);

    // TODO: Elimina i servizi qui sotto
    void temp_verifyRegistrationByEmail(String email);
    List<Token> temp_getListOfTokenByEmail(String email);
}
