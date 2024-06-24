package com.candido.server.controller.auth;

import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.request.auth.RequestRegisterEmailVerify;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
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
@RequestMapping("/api/v1/auth/register/email")
@RequiredArgsConstructor
public class ControllerAuthEmailBasedRegistration {

    private final AuthenticationService authenticationService;

    private final UtilService utilService;

    private final EncryptionService encryptionService;

    @PostMapping
    public ResponseEntity<ResponseAuthentication> registerByEmail(
            @Valid @RequestBody RequestRegister request,
            HttpServletRequest httpRequest
    ) {
        String clientIP = utilService.getClientIP(httpRequest);
        String appURL = utilService.getAppUrl(httpRequest);
        authenticationService.registerByEmail(request, clientIP, appURL);

        var requestAuthentication = new RequestAuthentication(request.email(), request.password());
        var responseAuthentication = authenticationService.authenticate(requestAuthentication, clientIP);

        return ResponseEntity.ok(responseAuthentication);
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyEmailRegistrationByUUIDAccessToken(
            @Valid @RequestBody RequestRegisterEmailVerify request
    ) {
        String uuidAccessToken = request.uuidAccessToken();
        String encryptedEmail = request.encryptedEmail();

        String email = encryptionService.decrypt(encryptedEmail);
        authenticationService.verifyEmailRegistration(uuidAccessToken, email);
        return ResponseEntity.noContent().build();
    }

}
