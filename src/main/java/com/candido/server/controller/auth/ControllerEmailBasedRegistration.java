package com.candido.server.controller.auth;

import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.service.auth.AuthenticationService;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/register/email")
@RequiredArgsConstructor
public class ControllerEmailBasedRegistration {

    private final AuthenticationService authenticationService;

    private final UtilService utilService;

    @PostMapping
    public ResponseEntity<Void> registerByEmail(
            @Valid @RequestBody RequestRegister request,
            HttpServletRequest httpRequest
    ) {
        String clientIP = utilService.getClientIP(httpRequest);
        String appURL = utilService.getAppUrl(httpRequest);
        authenticationService.registerByEmail(request, clientIP, appURL);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyEmailRegistrationByUUIDAccessToken(
            @RequestParam("t") String uuidAccessToken
    ) {
        authenticationService.verifyRegistrationByUUIDAccessToken(uuidAccessToken);
        return ResponseEntity.noContent().build();
    }

}
