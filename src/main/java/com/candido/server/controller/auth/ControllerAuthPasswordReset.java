package com.candido.server.controller.auth;

import com.candido.server.dto.v1.request.auth.RequestPasswordReset;
import com.candido.server.dto.v1.request.auth.RequestPasswordResetEmail;
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
@RequestMapping("/api/v1/auth/reset-password")
@RequiredArgsConstructor
public class ControllerAuthPasswordReset {

    private final AuthenticationService authenticationService;

    private final UtilService utilService;

    private final EncryptionService encryptionService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendResetPassword(
            @Valid @RequestBody RequestPasswordResetEmail requestPasswordResetEmail,
            HttpServletRequest httpRequest
    ) {
        authenticationService.sendResetPassword(
                requestPasswordResetEmail.email(),
                utilService.getClientIP(httpRequest),
                utilService.getAppUrl(httpRequest)
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-validity")
    public ResponseEntity<Void> checkValidityOfUUIDAccessTokenForResetPassword(
            @RequestParam("t") String uuidAccessToken
    ) {
        authenticationService.checkValidityOfUUIDAccessTokenForResetPassword(uuidAccessToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseAuthentication> resetPassword(
            @Valid @RequestBody RequestPasswordReset request,
            HttpServletRequest httpRequest
    ) {
        String uuidAccessToken = request.uuidAccessToken();
        String encryptedEmail = request.encryptedEmail();

        String email = encryptionService.decrypt(encryptedEmail);
        ResponseAuthentication authentication = authenticationService.resetPassword(
                uuidAccessToken,
                email,
                request,
                utilService.getClientIP(httpRequest)
        );
        
        return ResponseEntity.ok(authentication);
    }


}
