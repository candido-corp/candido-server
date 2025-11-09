package com.candido.server.controller.auth;

import com.candido.server.domain.v1.token.Token;
import com.candido.server.dto.v1.request.auth.RequestAuthentication;
import com.candido.server.dto.v1.response.auth.ResponseAuthentication;
import com.candido.server.dto.v1.response.auth.ResponseToken;
import com.candido.server.service.base.auth.AuthenticationService;
import com.candido.server.service.base.mapper.TokenMapperService;
import com.candido.server.util.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ControllerAuth {

    private final AuthenticationService authenticationService;

    private final UtilService utilService;

    private final TokenMapperService tokenMapper;

    @PostMapping("/login")
    public ResponseEntity<ResponseAuthentication> login(
            @Valid @RequestBody RequestAuthentication request,
            HttpServletRequest httpRequest
    ) {
        String clientIP = utilService.getClientIP(httpRequest);
        ResponseAuthentication authentication = authenticationService.authenticate(request, clientIP);
        return ResponseEntity.ok(authentication);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<ResponseAuthentication> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        ResponseAuthentication authentication = authenticationService.refreshToken(request, response);
        return ResponseEntity.ok(authentication);
    }

    @PostMapping("/register-verify/email/{email}")
    public ResponseEntity<Void> verifyRegistrationByEmail(
            @PathVariable("email") String email
    ) {
        // TODO: Delete this endpoint
        authenticationService.temp_verifyRegistrationByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/token/{email}")
    public ResponseEntity<List<ResponseToken>> getTokenList(
            @PathVariable("email") String email
    ) {
        // TODO: Delete this endpoint
        List<Token> tokenList = authenticationService.temp_getListOfTokenByEmail(email);
        List<ResponseToken> responseTokenList = tokenList
                .stream().map(token -> tokenMapper.tokenToTokenDto(token)).collect(Collectors.toList());

        return ResponseEntity.ok(responseTokenList);
    }

}
