package com.candido.server.controller.auth;

import com.candido.server.domain.v1.token.Token;
import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.request.auth.RequestRegister;
import com.candido.server.dto.v1.request.auth.RequestRegisterVerifyTemporaryCode;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.dto.v1.response.auth.ResponseCheckEmail;
import com.candido.server.dto.v1.response.auth.ResponseRegistration;
import com.candido.server.dto.v1.response.auth.ResponseToken;
import com.candido.server.service.auth.AuthenticationService;
import com.candido.server.service.mapstruct.TokenMapper;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    UtilService utilService;

    @Autowired
    TokenMapper tokenMapper;

    @PostMapping("/check-email/{email}")
    public ResponseEntity<ResponseCheckEmail> checkEmailAvailability(@PathVariable("email") String email) {
        return ResponseEntity.ok(new ResponseCheckEmail(authenticationService.checkEmailAvailability(email)));
    }



    @PostMapping("/authenticate")
    public ResponseEntity<ResponseAuthentication> authenticate(
            @RequestBody RequestAuthentication request, HttpServletRequest httpRequest
    ) {
        String clientIP = utilService.getClientIP(httpRequest);
        return ResponseEntity.ok(authenticationService.authenticate(request, clientIP));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseAuthentication> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.refreshToken(request, response));
    }

    @PostMapping("/register-verify/email/{email}")
    public ResponseEntity<Void> verifyRegistrationByEmail(
            @PathVariable("email") String email
    ) {
        // TODO: Delete this endpoint
        authenticationService.verifyRegistrationByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/token/{email}")
    public ResponseEntity<List<ResponseToken>> getTokenList(
            @PathVariable("email") String email
    ) {
        // TODO: Delete this endpoint
        List<Token> tokenList = authenticationService.getListOfTokenByEmail(email);
        List<ResponseToken> responseTokenList = tokenList
                .stream().map(token -> tokenMapper.tokenToTokenDto(token)).collect(Collectors.toList());

        return ResponseEntity.ok(responseTokenList);
    }

}
