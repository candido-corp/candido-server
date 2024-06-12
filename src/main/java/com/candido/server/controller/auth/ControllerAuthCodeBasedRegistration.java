package com.candido.server.controller.auth;

import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.request.auth.RequestRegisterCodeResend;
import com.candido.server.dto.v1.request.auth.RequestRegisterCodeVerify;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import com.candido.server.service.base.auth.AuthenticationService;
import com.candido.server.util.EncryptionService;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/auth/register/code")
@RequiredArgsConstructor
public class ControllerAuthCodeBasedRegistration {

    private final AuthenticationService authenticationService;

    private final UtilService utilService;

    private final EncryptionService encryptionService;

    @PostMapping
    public ResponseEntity<ResponseRegistration> registerByCode(
            @Valid @RequestBody RequestRegister request,
            HttpServletRequest httpRequest
    ) {
        String clientIP = utilService.getClientIP(httpRequest);
        String appURL = utilService.getAppUrl(httpRequest);
        ResponseRegistration response = authenticationService.registerByCode(request, clientIP, appURL);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend")
    public ResponseEntity<Void> resendCodeForCodeVerification(
            @Valid @RequestBody RequestRegisterCodeResend request,
            HttpServletRequest httpRequest
    ) {
        String uuidAccessToken = request.uuidAccessToken();

        String appURL = utilService.getAppUrl(httpRequest);
        authenticationService.resendCodeRegistrationByUUIDAccessToken(uuidAccessToken, appURL);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyCodeRegistrationBySessionIdAndTemporaryCode(
            @Valid @RequestBody RequestRegisterCodeVerify request
    ) {
        String uuidAccessToken = request.uuidAccessToken();
        String encryptedEmail = request.encryptedEmail();

        String email = encryptionService.decrypt(encryptedEmail);
        authenticationService.verifyCodeRegistration(uuidAccessToken, request.temporaryCode(), email);
        return ResponseEntity.noContent().build();
    }

}
