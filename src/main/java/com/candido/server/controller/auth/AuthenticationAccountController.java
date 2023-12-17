package com.candido.server.controller.auth;

import com.candido.server.domain.v1.token.TokenScopeCategoryEnum;
import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.service.auth.AuthenticationServiceImpl;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationAccountController {

    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Autowired
    UtilService utilService;

    @PostMapping("/reset-password-send/{email}")
    public ResponseEntity<Void> sendResetPassword(@PathVariable("email") String email, HttpServletRequest httpRequest) {
        authenticationServiceImpl.sendResetPassword(email, utilService.getClientIP(httpRequest), utilService.getAppUrl(httpRequest));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password-verify/{token}")
    public ResponseEntity<Void> verifyResetToken(@PathVariable("token") String token) {
        // TODO: Togli il ritardo
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        authenticationServiceImpl.getAccountAndVerifyToken(token, TokenScopeCategoryEnum.BTD_RESET.getTokenScopeCategoryId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<ResponseAuthentication> resetPassword(
            @RequestBody RequestPasswordReset request,
            @PathVariable("token") String token,
            HttpServletRequest httpRequest
    ) {
        // TODO: Togli il ritardo
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(authenticationServiceImpl.resetPassword(token, request, utilService.getClientIP(httpRequest)));
    }


}
