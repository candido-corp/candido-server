package com.candido.server.service.auth;

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
    void registerByEmail(RequestRegister request, String ipAddress, String appUrl);
    ResponseRegistration registerByCode(RequestRegister request, String ipAddress, String appUrl);

    void verifyRegistrationByUUIDAccessToken(String uuidAccessToken);
    void verifyRegistrationByUUIDAccessTokenAndTemporaryCode(String uuidAccessToken, String temporaryCode);
    void checkValidityOfUUIDAccessTokenForResetPassword(String uuidAccessToken);
    void resendCodeRegistrationByUUIDAccessToken(String uuidAccessToken, String appUrl);

    ResponseAuthentication authenticate(RequestAuthentication request, String ipAddress);
    ResponseAuthentication refreshToken(HttpServletRequest request, HttpServletResponse response);
    void sendResetPassword(String email, String ipAddress, String appUrl);
    ResponseAuthentication resetPassword(String uuidAccessToken, RequestPasswordReset request, String ipAddress);


    // TODO: Elimina i servizi qui sotto
    void verifyRegistrationByEmail(String email);
    List<Token> getListOfTokenByEmail(String email);
}
