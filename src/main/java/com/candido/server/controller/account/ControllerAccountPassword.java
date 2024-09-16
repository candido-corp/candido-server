package com.candido.server.controller.account;

import com.candido.server.service.base.auth.AuthenticationService;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/me/password")
public class ControllerAccountPassword {

    private final AuthenticationService authenticationService;

    private final UtilService utilService;

    @Autowired
    public ControllerAccountPassword(
            AuthenticationService authenticationService,
            UtilService utilService
    ) {
        this.authenticationService = authenticationService;
        this.utilService = utilService;
    }

    @PostMapping
    public ResponseEntity<Void> editAccountPassword(
            Authentication authentication,
            HttpServletRequest httpRequest
    ) {
        authenticationService.sendResetPassword(
                authentication.getName(),
                utilService.getClientIP(httpRequest),
                utilService.getAppUrl(httpRequest)
        );
        return ResponseEntity.noContent().build();
    }

}
