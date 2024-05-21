package com.candido.server.controller.auth;

import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.request.auth.RequestRegisterVerifyTemporaryCode;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import com.candido.server.service.base.auth.AuthenticationService;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/register/code")
@RequiredArgsConstructor
public class ControllerAuthCodeBasedRegistration {

    private final AuthenticationService authenticationService;

    private final UtilService utilService;

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
            @RequestParam("t") String uuidAccessToken,
            HttpServletRequest httpRequest
    ) {
        String appURL = utilService.getAppUrl(httpRequest);
        authenticationService.resendCodeRegistrationByUUIDAccessToken(uuidAccessToken, appURL);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyCodeRegistrationBySessionIdAndTemporaryCode(
            @RequestParam("t") String uuiAccessToken,
            @Valid @RequestBody RequestRegisterVerifyTemporaryCode request
    ) {
        authenticationService.verifyRegistrationByUUIDAccessTokenAndTemporaryCode(uuiAccessToken, request.temporaryCode());
        return ResponseEntity.noContent().build();
    }

}
