package com.candido.server.controller.auth;

import com.candido.server.domain.v1.token.Token;
import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.request.auth.RequestRegisterVerifyTemporaryCode;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import com.candido.server.service.auth.AuthenticationService;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    UtilService utilService;

    private ResponseEntity<ResponseRegistration> executeRegistration(
            RequestRegister request, HttpServletRequest httpRequest, boolean isEmailVerification
    ) {
        String clientIP = utilService.getClientIP(httpRequest);
        String appURL = utilService.getAppUrl(httpRequest);
        ResponseRegistration response = authenticationService.register(request, clientIP, appURL, isEmailVerification);
        return isEmailVerification ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PostMapping("/register/email-verification")
    public ResponseEntity<ResponseRegistration> registerThroughEmailVerification(
            @Valid @RequestBody RequestRegister request,
            HttpServletRequest httpRequest
    ) {
        return executeRegistration(request, httpRequest, true);
    }

    @PostMapping("/register/code-verification")
    public ResponseEntity<ResponseRegistration> registerThroughCodeVerification(
            @Valid @RequestBody RequestRegister request,
            HttpServletRequest httpRequest
    ) {
        return executeRegistration(request, httpRequest, false);
    }

    @PostMapping("/register/code-verification/session/{sessionId}/resend-code")
    public ResponseEntity<Void> resendCodeForCodeVerification(
            @PathVariable("sessionId") String sessionId
    ) {
        authenticationService.resendCodeRegistrationBySessionId(sessionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register-verify/{token}")
    public ResponseEntity<Void> verifyRegistrationByToken(
            @PathVariable("token") String token
    ) {
        authenticationService.verifyRegistrationByToken(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register-verify/session/{sessionId}")
    public ResponseEntity<Void> verifyRegistrationBySessionIdAndTemporaryCode(
            @PathVariable("sessionId") String sessionId,
            @Valid @RequestBody RequestRegisterVerifyTemporaryCode request
    ) {
        authenticationService.verifyRegistrationBySessionIdAndTemporaryCode(sessionId, request.temporaryCode());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseAuthentication> authenticate(
            @RequestBody RequestAuthentication request, HttpServletRequest httpRequest
    ) {
        String clientIP = utilService.getClientIP(httpRequest);
        return ResponseEntity.ok(authenticationService.authenticate(request, clientIP));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseAuthentication> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.refreshToken(request, response));
    }

    @PostMapping("/register-verify/email/{email}")
    public ResponseEntity<Void> verifyRegistrationByEmail(
            @PathVariable("email") String email
    ) {
        // TODO: Delete this endpoint
        authenticationService.verifyRegistrationByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/token/{email}")
    public ResponseEntity<List<Token>> getTokenList(
            @PathVariable("email") String email
    ) {
        // TODO: Delete this endpoint
        return ResponseEntity.ok(
                authenticationService.getListOfTokenByEmail(email)
        );
    }

}
