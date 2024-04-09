package com.candido.server.controller.auth;

import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.request.auth.RequestRegisterVerifyTemporaryCode;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import com.candido.server.service.auth.AuthenticationService;
import com.candido.server.service.mapstruct.TokenMapper;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/register")
@RequiredArgsConstructor
public class AuthenticationRegisterController {

    private final AuthenticationService authenticationService;

    @Autowired
    UtilService utilService;

    @PostMapping("/email")
    public ResponseEntity<Void> registerByEmail(
            @Valid @RequestBody RequestRegister request,
            HttpServletRequest httpRequest
    ) {
        String clientIP = utilService.getClientIP(httpRequest);
        String appURL = utilService.getAppUrl(httpRequest);
        authenticationService.registerByEmail(request, clientIP, appURL);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/code")
    public ResponseEntity<ResponseRegistration> registerByCode(
            @Valid @RequestBody RequestRegister request,
            HttpServletRequest httpRequest
    ) {
        String clientIP = utilService.getClientIP(httpRequest);
        String appURL = utilService.getAppUrl(httpRequest);
        ResponseRegistration response = authenticationService.registerByCode(request, clientIP, appURL);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/code/resend")
    public ResponseEntity<Void> resendCodeForCodeVerification(
            @PathVariable("sessionId") String sessionId,
            HttpServletRequest httpRequest
    ) {
        String appURL = utilService.getAppUrl(httpRequest);
        authenticationService.resendCodeRegistrationBySessionId(sessionId, appURL);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/email/verify/{token}")
    public ResponseEntity<Void> verifyEmailRegistrationByToken(
            @PathVariable("token") String token
    ) {
        authenticationService.verifyRegistrationByToken(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/code/verify/{sessionId}")
    public ResponseEntity<Void> verifyCodeRegistrationBySessionIdAndTemporaryCode(
            @PathVariable("sessionId") String sessionId,
            @Valid @RequestBody RequestRegisterVerifyTemporaryCode request
    ) {
        authenticationService.verifyRegistrationBySessionIdAndTemporaryCode(sessionId, request.temporaryCode());
        return ResponseEntity.noContent().build();
    }

}
