package com.candido.server.controller.auth;

import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.service.auth.AuthenticationServiceImpl;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Autowired
    UtilService utilService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RequestRegister request, HttpServletRequest httpRequest
    ) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        authenticationServiceImpl.register(request, utilService.getClientIP(httpRequest), utilService.getAppUrl(httpRequest));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/register-verify/{token}")
    public ResponseEntity<Void> verifyRegistrationToken(
            @PathVariable("token") String token
    ) {
        // TODO: Togli il ritardo
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        authenticationServiceImpl.verifyRegistrationToken(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseAuthentication> authenticate(
            @RequestBody RequestAuthentication request, HttpServletRequest httpRequest
    ) {
        // TODO: Togli il ritardo
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(authenticationServiceImpl.authenticate(request, utilService.getClientIP(httpRequest)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseAuthentication> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationServiceImpl.refreshToken(request, response));
    }

}
