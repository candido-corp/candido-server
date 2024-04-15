package com.candido.server.controller.auth;

import com.candido.server.domain.v1.token.TokenScopeCategoryEnum;
import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.service.auth.AuthenticationService;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final AuthenticationService authenticationService;

    private final UtilService utilService;

    @PostMapping("/reset-password/send/{email}")
    public ResponseEntity<Void> sendResetPassword(@PathVariable("email") String email, HttpServletRequest httpRequest) {
        authenticationService.sendResetPassword(email, utilService.getClientIP(httpRequest), utilService.getAppUrl(httpRequest));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password/verify/{token}")
    public ResponseEntity<Void> verifyResetToken(@PathVariable("token") String token) {
        authenticationService.getAccountAndVerifyToken(token, TokenScopeCategoryEnum.BTD_RESET.getTokenScopeCategoryId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<ResponseAuthentication> resetPassword(
            @PathVariable("token") String token,
            @RequestBody RequestPasswordReset request,
            HttpServletRequest httpRequest
    ) {
        ResponseAuthentication authentication = authenticationService.resetPassword(token, request, utilService.getClientIP(httpRequest));
        return ResponseEntity.ok(authentication);
    }


}
